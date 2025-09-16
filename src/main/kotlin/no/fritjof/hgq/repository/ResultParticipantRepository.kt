package no.fritjof.hgq.repository

import no.fritjof.hgq.model.ResultParticipant
import no.fritjof.hgq.model.ResultParticipantId
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ResultParticipantRepository : JpaRepository<ResultParticipant, ResultParticipantId> {
    fun deleteByResultDate(resultDate: LocalDate)
}
