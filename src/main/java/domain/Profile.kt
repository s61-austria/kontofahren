package domain

import exceptions.KontoException
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "profile")
data class Profile(
    @OneToOne
    var kontoUser: KontoUser?
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()

    @ManyToMany(targetEntity = Vehicle::class)
    var vehicles: MutableList<Vehicle> = mutableListOf()

    @OneToMany(targetEntity = Invoice::class)
    var invoices: MutableList<Invoice> = mutableListOf()

    @Throws(KontoException::class)
    fun addVehicle(vehicle: Vehicle) {
        if (!vehicles.contains(vehicle)) {
            vehicles.add(vehicle)
        }
        throw KontoException("kontoUser already owns car")
    }

    @Throws(KontoException::class)
    fun removeVehicle(vehicle: Vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles.remove(vehicle)
        } else {
            throw KontoException("kontoUser does not own this car " + vehicle.hardwareSerialNumber)
        }
    }

    @Throws(KontoException::class)
    fun addInvoice(invoice: Invoice) {
        if (!invoices.contains(invoice)) {
            invoices.add(invoice)
        } else {
            throw KontoException("kontoUser already has invoice")
        }
    }
}
