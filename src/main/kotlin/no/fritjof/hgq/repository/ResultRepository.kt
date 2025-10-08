package no.fritjof.hgq.repository

import no.fritjof.hgq.model.Result
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ResultRepository : JpaRepository<Result, LocalDate> {

    @Query("SELECT DISTINCT r.quizSource FROM Result r")
    fun findDistinctQuizSources(): List<String>

}
