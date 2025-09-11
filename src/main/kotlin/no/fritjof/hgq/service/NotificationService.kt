package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationService() {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun notify(event: Event) {
        logger.info("User [${event.user}] [${event.type}] result for [${event.data.date}] to [${event.data.score}/${event.data.total}]")
    }

}
