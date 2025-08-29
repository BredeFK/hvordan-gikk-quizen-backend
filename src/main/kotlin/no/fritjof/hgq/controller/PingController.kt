package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Ping")
@Controller
@RequestMapping("/api/ping")
class PingController {

    @GetMapping(produces = ["text/plain"])
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok("pong")
    }

}
