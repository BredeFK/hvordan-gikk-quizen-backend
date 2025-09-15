package no.fritjof.hgq.repository

import no.fritjof.hgq.model.Participant
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository : JpaRepository<Participant, Long>
