package no.fritjof.hgq.config

import org.apache.http.HttpHeaders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class WebClientConfig(
    @Value($$"${services.slack.webhook-url}") private val webHookUrl: String
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun slackWebClient(): WebClient {
        return WebClient.builder()
            .filter(requestLoggerFilter())
            .filter(responseLoggerFilter())
            .baseUrl(webHookUrl)
            .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .build()
    }

    private fun requestLoggerFilter() = ExchangeFilterFunction.ofRequestProcessor {
        logger.info("${it.method()} ${it.url()}")
        Mono.just(it)
    }

    private fun responseLoggerFilter() = ExchangeFilterFunction.ofResponseProcessor {
        logger.info("${it.request().method} ${it.request().uri} [${it.statusCode().value()}]")
        Mono.just(it)
    }

}
