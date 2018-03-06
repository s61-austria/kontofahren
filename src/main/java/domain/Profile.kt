package domain

import exceptions.KontoException

import javax.persistence.*

@Entity
class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @OneToOne
    val user: User? = null

    @ManyToMany
    var vehicles: List<Vehicle> = emptyList()

    @OneToMany
    var invoices: List<Invoice> = emptyList()

    @Throws(KontoException::class)
    fun addVehicle(vehicle: Vehicle) {
        if (!vehicles.contains(vehicle)) {
            vehicles += vehicle
        }
        throw KontoException("user already owns car")
    }

    @Throws(KontoException::class)
    fun removeVehicle(vehicle: Vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles -=vehicle
        } else {
            throw KontoException("user does not own this car " + vehicle.hardwareSerialNumber)
        }
    }

    @Throws(KontoException::class)
    fun addInvoice(invoice: Invoice) {
        if (!invoices.contains(invoice)) {
            invoices += invoice
        } else {
            throw KontoException("user already has invoice")
        }
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
