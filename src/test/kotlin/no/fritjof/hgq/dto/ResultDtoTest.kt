package no.fritjof.hgq.dto

import no.fritjof.hgq.model.Result
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ResultDtoTest {

    @Test
    fun `test ResultDto conversion from Result with 10 questions`() {
        val date = LocalDate.of(2025, 10, 27)
        val result = Result(
            date = date,
            score = 8,
            total = 10
        )
        
        val dto = ResultDto.fromResult(result)
        
        assertEquals(date, dto.date)
        assertEquals(8, dto.score)
        assertEquals(10, dto.total)
        assertEquals(80.0, dto.percentage)
    }

    @Test
    fun `test ResultDto conversion from Result with 14 questions`() {
        val date = LocalDate.of(2025, 10, 27)
        val result = Result(
            date = date,
            score = 12,
            total = 14
        )
        
        val dto = ResultDto.fromResult(result)
        
        assertEquals(date, dto.date)
        assertEquals(12, dto.score)
        assertEquals(14, dto.total)
        assertEquals(85.7, dto.percentage)
    }
}
