package no.fritjof.hgq.repository

import no.fritjof.hgq.model.Result
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ResultRepository : JpaRepository<Result, LocalDate>
