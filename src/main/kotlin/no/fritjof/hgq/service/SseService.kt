package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList

@Service
class SseService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val emitters = CopyOnWriteArrayList<SseEmitter>()

    fun subscribe(): SseEmitter {
        val emitter = SseEmitter(0L)
        emitters.add(emitter)

        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout {
            emitters.remove(emitter)
            emitter.complete()
        }
        emitter.onError { _ ->
            emitters.remove(emitter)
            try {
                emitter.complete()
            } catch (_: Exception) {
                // Do nothing
            }
        }

        try {
            emitter.send(SseEmitter.event().name("init").data("connected"))
        } catch (e: IOException) {
            logger.debug("Failed to send init event to SSE client: {}", e.message)
        }

        return emitter
    }

    @Async
    fun broadcast(event: Event) {
        for (emitter in emitters.toList()) {
            try {
                emitter.send(SseEmitter.event().name("result").data(event))
            } catch (_: Exception) {
                emitters.remove(emitter)
                try {
                    emitter.complete()
                } catch (_: Exception) {
                    // Do nothing
                }
            }
        }
    }
}
