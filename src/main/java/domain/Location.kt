package domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "location")
data class Location(
    @ManyToOne
    val country: Country,
    val latitude: Double,
    val longitude: Double,
    @Temporal(TemporalType.DATE)
    val creationDate: Date
) {
    @Id
    private val id: String = UUID.randomUUID().toString()
}
