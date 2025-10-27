package no.fritjof.hgq.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate
import kotlin.math.roundToInt

@Entity
data class Result(

    @Id
    val date: LocalDate = LocalDate.now(),
    var score: Int = 0,
    var total: Int = 10

) {
    fun getPercentage(): Double {
        if (total == 0) return 0.0
        return (score.toDouble() / total.toDouble() * 100.0 * 10).roundToInt() / 10.0
    }
}
