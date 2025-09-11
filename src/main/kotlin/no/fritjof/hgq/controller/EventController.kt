package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.service.NotificationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Tag(name = "Event Controller")
@RestController
@RequestMapping("/api/events")
class EventController(private val notificationService: NotificationService) {

    @GetMapping("/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @Operation(
        summary = "Subscribe to server-sent events",
        description = "Subscribe to server-sent events for altered results",
    )
    fun stream(): SseEmitter = notificationService.subscribe()

}

