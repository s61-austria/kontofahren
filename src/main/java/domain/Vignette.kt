package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import org.hibernate.annotations.GenericGenerator

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
class Vignette : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    /*@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    val uuid: UUID? = null*/

    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType? = null
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType? = null
    @Column
    var price: Double = 0.toDouble()

    constructor() {}

    constructor(vehicleType: VehicleType, vignetteType: VignetteType, price: Double) {
        //this.id = UUID.randomUUID();
        this.vehicleType = vehicleType
        this.vignetteType = vignetteType
        this.price = price
    }

}
