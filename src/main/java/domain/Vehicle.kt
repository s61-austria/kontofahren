package domain

import domain.enums.VehicleType
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "vehicle")
data class Vehicle(
    var hardwareSerialNumber: String,
    var licensePlate: String,
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var owner: Profile? = null
) :Base() {

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()

    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var activities: List<Activity> = emptyList()

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(nullable = true)
    var currentLocation: Location? = null
}
