package domain

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
    var activities: List<Activity>? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var owner: Profile? = null
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var currentLocation: Location? = null

    constructor() {}

    constructor(hardwareSerialNumber: String, vehicleType: VehicleType, currentLocation: Location) {
        this.hardwareSerialNumber = hardwareSerialNumber
        this.vehicleType = vehicleType
        this.currentLocation = currentLocation
        this.activities = ArrayList()
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
