package domain

import domain.enums.VehicleType
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "vehicle")
data class Vehicle(
    var hardwareSerialNumber: String,
    var licensePlate: String,
    @Enumerated(EnumType.STRING)
    var vehicleType: VehicleType,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var owner: Profile? = null,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var rate: Rate
) {
    @Id
    var id: String = UUID.randomUUID().toString()

    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var activities: List<Activity> = emptyList()

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(nullable = true)
    var currentLocation: Location? = null
}
