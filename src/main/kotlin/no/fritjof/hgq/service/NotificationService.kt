package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList

@Service
class NotificationService() {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val emitters = CopyOnWriteArrayList<SseEmitter>()

    fun subscribe(): SseEmitter {
        val emitter = SseEmitter(0L)
        emitters.add(emitter)
        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout { emitters.remove(emitter) }
        emitter.onError { emitters.remove(emitter) }

        try {
            emitter.send(
                SseEmitter.event()
                    .name("connected")
                    .data(mapOf("status" to "ok"))
                    .id(System.currentTimeMillis().toString())
                    .reconnectTime(3000)
            )
        } catch (ex: IOException) {
            logger.error("Error sending connection message to client: ${ex.message}")
            emitters.remove(emitter)
        }
        return emitter
    }

    fun notify(event: Event) {
        logger.info(
            "User [${event.user}] [${event.type}] result for [${event.data.date}] to [${event.data.score}/${event.data.total}]"
        )

        val dead = mutableListOf<SseEmitter>()
        emitters.forEach { emitter ->
            try {
                emitter.send(
                    SseEmitter.event()
                        .name("result")
                        .data(event)
                        .id(System.currentTimeMillis().toString())
                )
            } catch (ex: Exception) {
                logger.error("Error sending event to client: ${ex.message}")
                dead.add(emitter)
            }
        }
        if (dead.isNotEmpty()) emitters.removeAll(dead)
    }

    @Scheduled(fixedRate = 25000)
    fun keepAlive() {
        if (emitters.isEmpty()) {
            return
        }
        val dead = mutableListOf<SseEmitter>()
        emitters.forEach { emitter ->
            try {
                emitter.send(SseEmitter.event().comment("keepalive"))
            } catch (ex: Exception) {
                logger.error("Error sending keepalive to client: ${ex.message}")
                dead.add(emitter)
            }
        }
        if (dead.isNotEmpty()) {
            emitters.removeAll(dead.toSet())
        }
    }
}
