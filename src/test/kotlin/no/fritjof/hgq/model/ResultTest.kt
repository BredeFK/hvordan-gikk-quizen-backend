package no.fritjof.hgq.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ResultTest {

    @Test
    fun `test percentage calculation for 10 questions`() {
        val result = Result(
            date = LocalDate.now(),
            score = 7,
            total = 10
        )
        assertEquals(70.0, result.getPercentage())
    }

    @Test
    fun `test percentage calculation for 14 questions`() {
        val result = Result(
            date = LocalDate.now(),
            score = 10,
            total = 14
        )
        assertEquals(71.4, result.getPercentage())
    }

    @Test
    fun `test percentage calculation with perfect score`() {
        val result = Result(
            date = LocalDate.now(),
            score = 14,
            total = 14
        )
        assertEquals(100.0, result.getPercentage())
    }

    @Test
    fun `test percentage calculation with zero score`() {
        val result = Result(
            date = LocalDate.now(),
            score = 0,
            total = 14
        )
        assertEquals(0.0, result.getPercentage())
    }

    @Test
    fun `test percentage calculation with zero total`() {
        val result = Result(
            date = LocalDate.now(),
            score = 5,
            total = 0
        )
        assertEquals(0.0, result.getPercentage())
    }

    @Test
    fun `test percentage rounding to one decimal place`() {
        val result = Result(
            date = LocalDate.now(),
            score = 11,
            total = 14
        )
        assertEquals(78.6, result.getPercentage())
    }
}
