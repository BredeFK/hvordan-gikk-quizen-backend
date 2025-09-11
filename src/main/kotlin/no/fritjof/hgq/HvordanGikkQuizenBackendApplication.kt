package no.fritjof.hgq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class HvordanGikkQuizenBackendApplication

fun main(args: Array<String>) {
    runApplication<HvordanGikkQuizenBackendApplication>(*args)
}
