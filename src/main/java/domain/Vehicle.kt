package domain

import afu.org.checkerframework.checker.igj.qual.Mutable
import domain.enums.VehicleType

import javax.persistence.*
import java.io.Serializable
import java.util.ArrayList

@Entity
@Table(name = "Vehicle")
class Vehicle : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var hardwareSerialNumber: String? = null
    var licensePlate: String? = null
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var activities: MutableList<Activity>? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var pastOwners: MutableList<Profile>? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var owner: Profile? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var currentLocation: Location? = null

    constructor() {}

    constructor(hardwareSerialNumber: String, vehicleType: VehicleType, currentLocation: Location, plate: String) {
        this.hardwareSerialNumber = hardwareSerialNumber
        this.vehicleType = vehicleType
        this.currentLocation = currentLocation
        this.activities = mutableListOf()
        this.licensePlate = plate
        this.pastOwners = mutableListOf()
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
