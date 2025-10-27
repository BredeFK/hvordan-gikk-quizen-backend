package no.fritjof.hgq.dto

import no.fritjof.hgq.model.Result
import java.time.LocalDate

data class ResultDto(
    val date: LocalDate,
    val score: Int,
    val total: Int,
    val percentage: Double
) {
    companion object {
        fun fromResult(result: Result): ResultDto {
            return ResultDto(
                date = result.date,
                score = result.score,
                total = result.total,
                percentage = result.getPercentage()
            )
        }
    }
}
