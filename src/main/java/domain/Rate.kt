package domain

import domain.enums.VehicleType
import domain.enums.VignetteType
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "rate")
data class Rate(
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType,
    @Enumerated(EnumType.STRING)
    var vignetteType: VignetteType,
    var kmPrice: Double = 0.toDouble()
) {
    @Id
    var id: String = UUID.randomUUID().toString()
}
