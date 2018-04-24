package domain

import org.joda.time.DateTime
import utils.now
import java.sql.Timestamp
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "activity")
data class Activity(
    @ManyToOne
    val rider: Profile,
    @ManyToOne
    val vehicle: Vehicle
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = EAGER, mappedBy = "activity")
    var locations: MutableList<Location> = mutableListOf()
    val creationDate: Timestamp = now()

    val creation: DateTime get() = DateTime(creationDate)
}
