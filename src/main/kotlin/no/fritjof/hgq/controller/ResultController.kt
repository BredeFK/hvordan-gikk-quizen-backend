package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.model.Result
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Result Controller")
@Controller
@RequestMapping("/api/result")
class ResultController() {

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get all results",
        description = "Return all results documented",
    )
    @ApiResponse(responseCode = "200", description = "List of results")
    fun getAllResults(): ResponseEntity<List<Result>> {
        return ResponseEntity.ok(
            listOf(
                Result(
                    date = "2023-01-01",
                    score = 10,
                    total = 10
                )
            )
        )
    }

}
