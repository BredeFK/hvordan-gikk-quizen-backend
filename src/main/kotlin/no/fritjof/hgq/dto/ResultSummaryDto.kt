package no.fritjof.hgq.dto

import no.fritjof.hgq.model.Result
import java.time.LocalDate

data class ResultSummaryDto(
    val date: LocalDate,
    val score: Int,
    val total: Int,
    val participantCount: Int
) {
    companion object {
        fun toDto(result: Result): ResultSummaryDto {
            return ResultSummaryDto(
                date = result.date,
                score = result.score,
                total = result.total,
                participantCount = result.participants.size
            )
        }
    }
}
