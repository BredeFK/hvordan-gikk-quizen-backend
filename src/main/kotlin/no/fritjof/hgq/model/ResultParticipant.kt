package no.fritjof.hgq.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDate

@Entity
@Table(name = "result_participant")
@IdClass(ResultParticipantId::class)
data class ResultParticipant(

    @Id
    @Column(name = "result_date")
    val resultDate: LocalDate,

    @Id
    @Column(name = "participant_id")
    val participantId: Long

)

data class ResultParticipantId(
    val resultDate: LocalDate = LocalDate.now(),
    val participantId: Long = 0
) : Serializable
