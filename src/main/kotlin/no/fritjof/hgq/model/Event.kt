package no.fritjof.hgq.model

data class Event(
    val type: EventType,
    val user: String,
    val data: Result
)
