package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EventService(
    private val sseService: SseService,
    private val slackService: SlackService,
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun publish(event: Event, sendSlack: Boolean) {
        logger.info("User [${event.user}] [${event.type}] result for [${event.data.date}] to [${event.data.score}/${event.data.total}] with source ${event.data.quizSource}")

        sseService.broadcast(event)
        if (sendSlack) {
            slackService.postSlackMessage(event)
        }
    }

}
