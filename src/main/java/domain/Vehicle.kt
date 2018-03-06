package domain

import domain.enums.VehicleType

import javax.persistence.*
import java.io.Serializable
import java.util.ArrayList

@Entity
class Vehicle : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var hardwareSerialNumber: String? = null
    var licensePlate: String? = null
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var activities: List<Activity>? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var owner: Civilian? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var currentLocation: Location? = null

    constructor() {}

    constructor(hardwareSerialNumber: String, vehicleType: VehicleType, currentLocation: Location, licensePlate: String) {
        this.hardwareSerialNumber = hardwareSerialNumber
        this.vehicleType = vehicleType
        this.currentLocation = currentLocation
        this.activities = ArrayList()
        this.licensePlate = licensePlate
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
