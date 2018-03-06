package domain

import domain.enums.VehicleType

import javax.persistence.*
import java.io.Serializable

@Entity
class Rate : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    @Enumerated
    var vt: VehicleType? = null
    @Column
    var kmPrice: Double = 0.toDouble()
    @Column
    var startPrice: Double = 0.toDouble()

    constructor() {}

    constructor(vt: VehicleType, kmPrice: Double, startPrice: Double) {
        this.vt = vt
        this.kmPrice = kmPrice
        this.startPrice = startPrice
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
