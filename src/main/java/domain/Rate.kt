package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import org.hibernate.annotations.GenericGenerator

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
class Rate : Serializable {

    //@Id
    //var id: UUID? = null

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    val uuid: UUID? = null

    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @Column
    var kmPrice: Double = 0.toDouble()
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType? = null

    constructor() {}

    constructor(vehicleType: VehicleType, kmPrice: Double, vignetteType: VignetteType) {
        //this.id = UUID.randomUUID()
        this.vehicleType = vehicleType
        this.kmPrice = kmPrice
        this.vignetteType = vignetteType
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
