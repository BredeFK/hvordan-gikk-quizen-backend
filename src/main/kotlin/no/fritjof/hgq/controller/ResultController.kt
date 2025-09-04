package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.model.Result
import no.fritjof.hgq.service.ResultService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Tag(name = "Result Controller")
@RestController
@RequestMapping("/api/result")
class ResultController(private val resultService: ResultService) {

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get all results",
        description = "Return all results documented",
    )
    @ApiResponse(responseCode = "200", description = "List of results")
    fun getAllResults(): ResponseEntity<List<Result>> {
        return ResponseEntity.ok(resultService.getAllResults())
    }

    @GetMapping("/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get result by id/date",
        description = "Return result if it exist and the date is valid",
    )
    @ApiResponse(responseCode = "200", description = "Result")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "400", description = "Date is not valid")
    fun getResultById(@PathVariable date: String): ResponseEntity<Result> {
        try {
            val parsedDate = LocalDate.parse(date)
            val result = resultService.getResult(parsedDate)
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
        summary = "Save a result",
        description = "Create or update a quiz result",
    )
    @ApiResponse(responseCode = "200", description = "The saved result")
    fun saveResult(@RequestBody result: Result): ResponseEntity<Result> {
        return ResponseEntity.ok(resultService.saveResult(result))
    }

}
