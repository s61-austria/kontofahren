package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import org.hibernate.annotations.GenericGenerator

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "rate")
class Rate : Serializable {

    @Id
    var id: String = UUID.randomUUID().toString()

    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @Column
    var kmPrice: Double = 0.toDouble()
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType? = null

    constructor(vehicleType: VehicleType, kmPrice: Double, vignetteType: VignetteType) {
        this.vehicleType = vehicleType
        this.kmPrice = kmPrice
        this.vignetteType = vignetteType
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
