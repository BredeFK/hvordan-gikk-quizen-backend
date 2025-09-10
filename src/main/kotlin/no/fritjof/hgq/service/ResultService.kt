package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import no.fritjof.hgq.model.EventType.INSERTED
import no.fritjof.hgq.model.EventType.UPDATED
import no.fritjof.hgq.model.Result
import no.fritjof.hgq.repository.ResultRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Optional

@Service
class ResultService(
    private val repository: ResultRepository,
    private val notificationService: NotificationService
) {

    fun getAllResults(): List<Result> = repository.findAll().sortedBy { it.date }

    fun getResult(localeDate: LocalDate): Optional<Result> = repository.findById(localeDate)

    fun saveResult(result: Result, email: String?): Result {
        val day = result.date.dayOfWeek
        require(!(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)) { "Cannot store scores for weekends" }

        val existingResult = repository.findById(result.date)
        if (existingResult.isPresent && existingResult.get() == result) {
            return existingResult.get()
        }

        val savedResult = repository.save(result)
        notificationService.notify(
            Event(
                type = if (existingResult.isPresent) UPDATED else INSERTED,
                user = email ?: "Unknown user",
                data = savedResult
            )
        )
        return savedResult
    }
}
