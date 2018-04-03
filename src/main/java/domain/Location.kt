package domain

import java.util.Date
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
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
    val point: Point,
    @Temporal(TemporalType.DATE)
    val creationDate: Date
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
