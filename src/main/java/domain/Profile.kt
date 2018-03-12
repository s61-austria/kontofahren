package domain

import exceptions.KontoException
import java.util.*

import javax.persistence.*

@Entity
class Profile {

    @Id
    var id: String = UUID.randomUUID().toString()

    @OneToOne
    val kontoUser: KontoUser? = null

    @ManyToMany
    var vehicles: List<Vehicle> = emptyList()

    @OneToMany
    var invoices: List<Invoice> = emptyList()

    @Throws(KontoException::class)
    fun addVehicle(vehicle: Vehicle) {
        if (!vehicles.contains(vehicle)) {
            vehicles += vehicle
        }
        throw KontoException("kontoUser already owns car")
    }

    @Throws(KontoException::class)
    fun removeVehicle(vehicle: Vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles -=vehicle
        } else {
            throw KontoException("kontoUser does not own this car " + vehicle.hardwareSerialNumber)
        }
    }

    @Throws(KontoException::class)
    fun addInvoice(invoice: Invoice) {
        if (!invoices.contains(invoice)) {
            invoices += invoice
        } else {
            throw KontoException("kontoUser already has invoice")
        }
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
