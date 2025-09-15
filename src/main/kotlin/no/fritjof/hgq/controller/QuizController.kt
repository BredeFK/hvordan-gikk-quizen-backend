package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.model.Quiz
import no.fritjof.hgq.service.QuizService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Quiz Controller")
@RestController
@RequestMapping("/api/quiz")
class QuizController(
    private val quizService: QuizService,
) {

    @GetMapping("/fetch", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get quiz info", description = "Fetch quiz info")
    @ApiResponse(responseCode = "200", description = "List of quiz's")
    fun fetchQuiz(): ResponseEntity<List<Quiz>> {

        return ResponseEntity.ok(quizService.getAllQuizzes())
    }
}




