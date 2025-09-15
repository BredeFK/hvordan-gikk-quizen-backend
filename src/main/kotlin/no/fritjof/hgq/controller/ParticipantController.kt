package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.model.Participant
import no.fritjof.hgq.service.ParticipantService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Participant Controller")
@RestController
@RequestMapping("/api/participant")
class ParticipantController(private val participantService: ParticipantService) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get all participants",
        description = "Return all participants."
    )
    @ApiResponse(responseCode = "200", description = "List of participants")
    fun getAllParticipants(@AuthenticationPrincipal user: OAuth2User?): ResponseEntity<List<Participant>> {
        if (user == null) {
            return ResponseEntity.status(401).build()
        }
        return ResponseEntity.ok(participantService.getAllParticipants())
    }

    @GetMapping("{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get participant by id",
        description = "Return participant with given id."
    )
    @ApiResponse(responseCode = "200", description = "Participant")
    fun getParticipant(
        @AuthenticationPrincipal user: OAuth2User?,
        @PathVariable id: Long
    ): ResponseEntity<Participant> {
        if (user == null) {
            return ResponseEntity.status(401).build()
        }
        val participant = participantService.getParticipant(id)
        if (participant.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(participant.get())
    }

}
