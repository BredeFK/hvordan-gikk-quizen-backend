package no.fritjof.hgq.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import no.fritjof.hgq.dto.GoogleUserDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "User Controller")
@Controller
@RequestMapping("/api")
class UserController() {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/user", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Get the current user info",
        description = "Return the user info of the logged in user",
    )
    @ApiResponse(responseCode = "200", description = "The user info")
    fun getUserInfo(@AuthenticationPrincipal user: OAuth2User?): ResponseEntity<GoogleUserDto> {
        if (user == null) {
            return ResponseEntity.ok(GoogleUserDto())
        }
        val googleUser = GoogleUserDto.toDto(user.attributes)
        logger.info("User [${googleUser.email}] logged in")
        return ResponseEntity.ok(googleUser)
    }

}
