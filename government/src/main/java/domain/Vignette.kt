package domain

import domain.enums.VehicleType
import domain.enums.VignetteType

import javax.persistence.*
import java.io.Serializable

/**
 * Created by Michel on 27-2-2018.
 */
@Entity
class Vignette : Serializable {
    @Id
    @GeneratedValue
    private val id: Long = 0

    @Enumerated
    var vehicleType: VehicleType? = null
    @Enumerated
    var vignetteType: VignetteType? = null
    @Column
    var price: Double = 0.toDouble()

    constructor() {}

    constructor(vehicleType: VehicleType, vignetteType: VignetteType, price: Double) {
        this.vehicleType = vehicleType
        this.vignetteType = vignetteType
        this.price = price
    }

}
