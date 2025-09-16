package no.fritjof.hgq.service

import no.fritjof.hgq.dto.ResultRequestDto
import no.fritjof.hgq.dto.ResultSummaryDto
import no.fritjof.hgq.model.Event
import no.fritjof.hgq.model.EventType.INSERTED
import no.fritjof.hgq.model.EventType.UPDATED
import no.fritjof.hgq.model.Result
import no.fritjof.hgq.model.ResultParticipant
import no.fritjof.hgq.repository.ResultParticipantRepository
import no.fritjof.hgq.repository.ResultRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Optional

@Service
class ResultService(
    private val repository: ResultRepository,
    private val resultParticipantRepository: ResultParticipantRepository,
    private val eventService: EventService
) {

    fun getAllResults(): List<ResultSummaryDto> {
        return repository.findAll().sortedBy { it.date }.map { ResultSummaryDto.toDto(it) }
    }

    fun getAllDetailedResults(): List<Result> {
        return repository.findAll().sortedBy { it.date }
    }

    fun getResult(localeDate: LocalDate): Optional<ResultSummaryDto> {
        val result = repository.findById(localeDate)
        if (!result.isPresent) {
            return Optional.empty()
        }
        return Optional.of(ResultSummaryDto.toDto(result.get()))
    }

    fun getDetailedResult(localDate: LocalDate): Optional<Result> {
        val result = repository.findById(localDate)
        if (!result.isPresent) {
            return Optional.empty()
        }
        return Optional.of(result.get())
    }

    fun saveResult(result: ResultRequestDto, email: String?, sendSlack: Boolean): Result {
        val day = result.date.dayOfWeek
        require(!(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)) { "Cannot store scores for weekends" }

        val existingResult = repository.findById(result.date)
        if (existingResult.isPresent && existingResult.get() == result) {
            return existingResult.get()
        }

        val savedResult = repository.save(
            Result(
                date = result.date,
                score = result.score,
                total = result.total,
            )
        )

        resultParticipantRepository.deleteByResultDate(savedResult.date)
        result.participantIds?.distinct()?.forEach { pid ->
            resultParticipantRepository.save(
                ResultParticipant(
                    resultDate = savedResult.date,
                    participantId = pid
                )
            )
        }

        eventService.publish(
            Event(
                type = if (existingResult.isPresent) UPDATED else INSERTED,
                user = email ?: "Unknown user",
                data = savedResult
            ),
            sendSlack
        )
        return savedResult
    }
}
