package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import org.hibernate.annotations.GenericGenerator

import javax.persistence.*
import java.io.Serializable
import java.util.*

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
