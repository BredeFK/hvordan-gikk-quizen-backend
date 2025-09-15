package no.fritjof.hgq.service

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.article
import it.skrape.selects.html5.h2
import it.skrape.selects.html5.img
import no.fritjof.hgq.model.Quiz
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.collections.map

@Service
class QuizService(
    @Value($$"${quiz.url}") val quizUrl: String,
) {

    fun getAllQuizzes(): List<Quiz> {
        return skrape(HttpFetcher) {
            request { url = quizUrl }
            response {
                htmlDocument {
                    article {
                        findAll {
                            map { el ->
                                val title = el.h2 { findFirst { text } }
                                val image = el.img { findFirst { attribute("src") } }
                                val url = el.a { findFirst { attribute("href") } }
                                Quiz(title = title, image = image, url = url)
                            }
                        }
                    }
                }
            }
        }
    }

}



