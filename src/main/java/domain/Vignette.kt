package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "vignette")
data class Vignette(
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType,
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType,
    var price: Double
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
