package no.fritjof.hgq.service

import no.fritjof.hgq.model.Event
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Service
class SlackService(
    @Qualifier("slackWebClient") private val webclient: WebClient,
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val locale = Locale.forLanguageTag("nb-NO")
    private val weekdayFormatter = DateTimeFormatter.ofPattern("EEEE", locale)
    private val dateFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", locale)


    fun postSlackMessage(event: Event) {
        if ((System.getenv("SPRING_PROFILES_ACTIVE") ?: "local") == "local") {
            logger.info("[SLACK_BOT] : Results for [${event.data.date}] -> ${event.data.score}/${event.data.total}")
            return
        }

        val body = formatMessage(event)
        webclient.post()
            .bodyValue(body)
            .retrieve()
            .toBodilessEntity()
            .doOnSuccess { logger.info("Posted message successfully to Slack") }
            .doOnError { error -> logger.error("Could not post message to Slack: ${error.message}") }
            .block()
    }

    private fun formatDate(date: LocalDate): String {
        val weekday = date.format(weekdayFormatter).replaceFirstChar { it.titlecase() }
        val dateString = date.format(dateFormatter)

        return "$weekday $dateString"
    }

    private fun getColour(score: Int) = when (score) {
        0 -> "#4d0000"
        1 -> "#660000"
        2 -> "#800000"
        3 -> "#a50026"
        4 -> "#d73027"
        5 -> "#f46d43"
        6 -> "#fdae61"
        7 -> "#fee08b"
        8 -> "#a6d96a"
        9 -> "#1a9850"
        10 -> "#006837"
        else -> "#4bb543"
    }

    private fun emoji(score: Int): String {
        return when (score) {
            10 -> ":penguin_dance:"
            9 -> ":flushed:"
            5 -> ":confuseddog:"
            1 -> ":face_with_cowboy_hat:"
            0 -> ":skull:"
            else -> ""
        }
    }

    private fun formatMessage(event: Event) = """
        {
            "attachments": [
                {
                    "color": "${getColour(event.data.score)}",
                    "blocks": [
                        {
                            "type": "header",
                            "text": {
                                "type": "plain_text",
                                "text": "Dagens quiz: ${formatDate(event.data.date)}"
                            }
                        },
                        {
                            "type": "header",
                            "text": {
                                "type": "plain_text",
                                "text": "${event.data.score}/${event.data.total}${emoji(event.data.score)}"
                            }
                        },
                        {
                            "type": "context",
                            "elements": [
                                {
                                    "type": "mrkdwn",
                                    "text": "<https://hvordangikkquizen.no/${event.data.date}|Resultat> | <https://hvordangikkquizen.no/statistikk|Statistikk> "
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    """.trimIndent()

}
