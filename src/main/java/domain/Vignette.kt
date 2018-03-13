package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import java.io.Serializable
import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "vignette")
class Vignette : Serializable {

    @Id
    var id: String = UUID.randomUUID().toString()

    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType? = null
    @Column
    var price: Double = 0.toDouble()

    constructor(vehicleType: VehicleType, vignetteType: VignetteType, price: Double) {
        this.vehicleType = vehicleType
        this.vignetteType = vignetteType
        this.price = price
    }
}
