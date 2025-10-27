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

    private fun formatMessage(event: Event) = """
        {
            "attachments": [
                {
                    "color": "#52A185",
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
                                "text": "${event.data.score}/${event.data.total}"
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
