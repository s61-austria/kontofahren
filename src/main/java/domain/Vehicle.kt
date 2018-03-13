package domain

import domain.enums.VehicleType
import java.io.Serializable
import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "vehicle")
class Vehicle : Serializable {

    @Id
    var id: String = UUID.randomUUID().toString()

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

    constructor(hardwareSerialNumber: String, vehicleType: VehicleType, currentLocation: Location, plate: String) {
        this.hardwareSerialNumber = hardwareSerialNumber
        this.vehicleType = vehicleType
        this.currentLocation = currentLocation
        this.activities = ArrayList()
        this.licensePlate = plate
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
