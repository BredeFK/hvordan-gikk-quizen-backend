package no.fritjof.hgq.dto

import java.time.LocalDate

data class ResultRequestDto(

    val date: LocalDate,
    var score: Int,
    var total: Int,
    var participantIds: List<Long>? = emptyList()

)
