package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "vignette")
data class Vignette(
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType,
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType,
    var price: Double
) :Base() {

}
