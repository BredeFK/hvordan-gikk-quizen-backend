package no.fritjof.hgq.dto

import no.fritjof.hgq.model.Result
import java.time.LocalDate

data class ResultDetailDto(
    val date: LocalDate,
    val score: Int,
    val total: Int,
    val participants: List<String>
) {
    companion object {
        fun toDto(result: Result): ResultDetailDto {
            return ResultDetailDto(
                date = result.date,
                score = result.score,
                total = result.total,
                participants = result.participants.map { it.name }
            )
        }
    }
}

