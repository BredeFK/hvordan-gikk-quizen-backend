package no.fritjof.hgq.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class Result(

    @Id
    val date: LocalDate = LocalDate.now(),
    var score: Int = 0,
    var total: Int = 10

)
