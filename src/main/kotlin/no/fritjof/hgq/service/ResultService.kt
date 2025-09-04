package no.fritjof.hgq.service

import no.fritjof.hgq.model.Result
import no.fritjof.hgq.repository.ResultRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Optional

@Service
class ResultService(private val repository: ResultRepository) {

    fun getAllResults(): List<Result> = repository.findAll().sortedBy { it.date }

    fun getResult(localeDate: LocalDate): Optional<Result> = repository.findById(localeDate)

    fun saveResult(result: Result): Result {
        val day = result.date.dayOfWeek
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            throw IllegalArgumentException("Cannot store scores for weekends")
        }
        return repository.save(result)
    }
}
