package domain

import org.joda.time.DateTime
import java.sql.Timestamp
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity(name = "Location")
@Table(name = "location")
data class Location(
    @ManyToOne
    val vehicle: Vehicle,
    val point: Point,
    val creationDate: Timestamp
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()

    @ManyToOne
    var activity: Activity? = null

    val creation get() = DateTime(creationDate)
}
