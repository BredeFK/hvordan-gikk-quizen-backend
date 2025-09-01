package no.fritjof.hgq.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/admin/**").authenticated()
                    .requestMatchers(
                        HttpMethod.GET,
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/results", "/api/ping").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.defaultSuccessUrl("/api/admin/me", true)
            }
            .logout {
                it.logoutUrl("/api/logout").logoutSuccessHandler { _, response, _ ->
                    response.status = 200
                }
            }
        return http.build()
    }

}
