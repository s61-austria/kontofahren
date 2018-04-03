package domain

import afu.org.checkerframework.checker.igj.qual.Mutable
import domain.enums.VehicleType
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "vehicle")
data class Vehicle(
    var hardwareSerialNumber: String,
    var licensePlate: String,
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var owner: Profile? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    lateinit var rate: Rate

    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var pastOwners: MutableList<Profile>? = mutableListOf()

    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var activities: List<Activity> = emptyList()

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(nullable = true)
    var currentLocation: Location? = null

    companion object {

        private val serialVersionUID = 1L
    }
}
