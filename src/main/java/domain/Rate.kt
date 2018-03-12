package domain

import domain.enums.VehicleType
import domain.enums.VignetteType

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
class Rate : Serializable {

    @Id
    var id: String? = UUID.randomUUID().toString()

    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @Column
    var kmPrice: Double = 0.toDouble()
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType? = null

    constructor() {}

    constructor(vehicleType: VehicleType, kmPrice: Double, vignetteType: VignetteType) {
        this.id = UUID.randomUUID().toString()
        this.vehicleType = vehicleType
        this.kmPrice = kmPrice
        this.vignetteType = vignetteType
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
