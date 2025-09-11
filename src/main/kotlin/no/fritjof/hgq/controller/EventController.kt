package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.service.SseService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Tag(name = "Event Controller")
@RestController
@RequestMapping("/api/event")
class EventController(
    private val sseService: SseService
) {

    @GetMapping(value = ["/stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun stream(): SseEmitter = sseService.subscribe()

}
