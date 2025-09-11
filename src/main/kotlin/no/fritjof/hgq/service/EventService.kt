package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EventService(
    private val sseService: SseService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun publish(event: Event) {
        logger.info("User [${event.user}] [${event.type}] result for [${event.data.date}] to [${event.data.score}/${event.data.total}]")

        sseService.broadcast(event)
        // Also: Post to Slack?
    }

}
