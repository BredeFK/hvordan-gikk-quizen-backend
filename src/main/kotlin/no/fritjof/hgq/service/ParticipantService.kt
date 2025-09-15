package no.fritjof.hgq.service

import no.fritjof.hgq.model.Participant
import no.fritjof.hgq.repository.ParticipantRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository
) {

    fun getAllParticipants(): List<Participant> = participantRepository.findAll().sortedBy { it.name }

    fun getParticipant(id: Long): Optional<Participant> = participantRepository.findById(id)
}
