package no.fritjof.hgq.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate

@Entity
data class Result(

    @Id
    val date: LocalDate = LocalDate.now(),
    var score: Int = 0,
    var total: Int = 10,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "result_participant",
        joinColumns = [JoinColumn(name = "result_date", referencedColumnName = "date")],
        inverseJoinColumns = [JoinColumn(name = "participant_id", referencedColumnName = "id")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["result_date", "participant_id"])]
    )
    var participants: MutableSet<Participant> = mutableSetOf()

)
