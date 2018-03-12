package domain

import domain.enums.VehicleType
import domain.enums.VignetteType

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
class Vignette : Serializable {

    @Id
    var id: String ?= UUID.randomUUID().toString()

    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType? = null
    @Column
    var price: Double = 0.toDouble()

    constructor() {}

    constructor(vehicleType: VehicleType, vignetteType: VignetteType, price: Double) {
        this.id = UUID.randomUUID().toString();
        this.vehicleType = vehicleType
        this.vignetteType = vignetteType
        this.price = price
    }

}
