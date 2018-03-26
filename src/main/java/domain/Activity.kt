package domain

import java.util.UUID
import javax.persistence.Column
import utils.now
import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "activity")
data class Activity(
    @ManyToOne
    val country: Country,
    @ManyToOne
    val rider: Profile
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var locations: List<Location> = emptyList()
    @Temporal(TemporalType.DATE)
    val creationDate: Date = now()
}
