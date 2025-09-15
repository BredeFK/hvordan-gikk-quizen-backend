package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.dto.GoogleUserDto
import no.fritjof.hgq.model.Result
import no.fritjof.hgq.service.ResultService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.Optional

@Tag(name = "Result Controller")
@RestController
@RequestMapping("/api/result")
class ResultController(private val resultService: ResultService) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get all results",
        description = "Return all documented results. " +
                "If the user is logged in, detailed results are returned as well, with the participant names",
    )
    @ApiResponse(responseCode = "200", description = "List of results")
    fun getAllResults(@AuthenticationPrincipal user: OAuth2User?): ResponseEntity<List<Any>> {
        val results: List<Any> = if (user == null) {
            resultService.getAllResults()
        } else {
            resultService.getAllDetailedResults()
        }
        return ResponseEntity.ok(results)
    }

    @GetMapping("/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get result by id/date",
        description = "Return result if it exist and the date is valid. " +
                "If the user is logged in, detailed result is returned as well, with the participant names",
    )
    @ApiResponse(responseCode = "200", description = "Result")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "400", description = "Date is not valid")
    fun getResultById(
        @PathVariable date: String,
        @AuthenticationPrincipal user: OAuth2User?,
    ): ResponseEntity<Any> {
        try {
            val parsedDate = LocalDate.parse(date)
            val result: Optional<out Any> = if (user == null) {
                resultService.getResult(parsedDate)
            } else {
                resultService.getDetailedResult(parsedDate)
            }
            if (result.isPresent) {
                return ResponseEntity.ok(result.get())
            }
            return ResponseEntity.notFound().build()
        } catch (_: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Save or update a result",
        description = "Create or update a quiz result",
    )
    @ApiResponse(responseCode = "200", description = "The saved result")
    fun saveResult(
        @AuthenticationPrincipal user: OAuth2User?,
        @RequestBody result: Result,
        @RequestParam("sendSlack") sendSlack: Boolean
    ): ResponseEntity<Result> {
        if (user == null) {
            return ResponseEntity.status(401).build()
        }
        val googleUser = GoogleUserDto.toDto(user.attributes)
        val response = resultService.saveResult(result, googleUser.email, sendSlack)
        return ResponseEntity.ok(response)
    }

}
