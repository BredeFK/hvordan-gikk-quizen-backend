package no.fritjof.hgq.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig(
    @Value($$"${cors.allowed-origin}") private val allowedOrigin: String
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        clientRegistrations: ObjectProvider<ClientRegistrationRepository>
    ): SecurityFilterChain? {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/actuator/health"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/result/**", "/api/ping", "/api/user", "/api/events/**").permitAll()
                    .anyRequest().authenticated()
            }

        if (clientRegistrations.ifAvailable != null) {
            http.oauth2Login {
                it.defaultSuccessUrl("$allowedOrigin/auth/success", true)
            }
        }

        http.logout {
            it.logoutUrl("/api/logout")
                .logoutSuccessHandler { _, response, _ ->
                    response.status = 200
                }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val cfg = CorsConfiguration().apply {
            allowedOrigins = listOf(allowedOrigin)
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/api/**", cfg)
        }
    }

}
