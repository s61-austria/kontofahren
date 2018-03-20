package service

import dao.InvoiceDao
import dao.UserDao
import domain.Activity
import domain.Country
import domain.Invoice
import domain.Location
import domain.Profile
import domain.Vehicle
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import utils.measureGeoDistance
import java.util.* // ktlint-disable no-wildcard-imports

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class InvoiceService @Inject constructor(
    val invoiceDao: InvoiceDao,
    val userDao: UserDao,
    val vehicleService: VehicleService
) {

    fun allInvoices(): List<Invoice> = invoiceDao.allInvoices()

    fun getInvoiceById(id: String): Invoice = invoiceDao.getInvoiceById(id)

    fun allInvoicesByVehicle(id: String): List<Invoice> = invoiceDao.allInvoicesByVehicle(id)

    fun allInvoicesByCivilian(id: String): List<Invoice> {
        val profile = userDao.getUserById(id) as Profile
        return profile.invoices
    }

    fun allInvoicesCreatedBetweenDates(start: String, end: String): List<Invoice> = invoiceDao.allInvoicesCreatedBetweenDates(Date(start.toLong()), Date(end.toLong()))

    fun allInvoicesForBetweenDates(start: String, end: String): List<Invoice> = invoiceDao.allInvoicesForBetweenDates(Date(start.toLong()), Date(end.toLong()))

    fun allInvoicesGeneratedBy(type: InvoiceGenerationType): List<Invoice> = invoiceDao.allInvoicesGeneratedBy(type)

    fun allInvoicesByState(state: InvoiceState): List<Invoice> = invoiceDao.allInvoicesByStatus(state)

    fun updateInvoiceState(invoiceId: String, state: InvoiceState): Invoice {
        val invoice = invoiceDao.getInvoiceById(invoiceId)
        invoice.state = state

        return invoiceDao.updateInvoice(invoice)
    }

    fun generateVehiclesInvoices(country: Country, month: Date) {
        val vehicles: List<Vehicle> = vehicleService.allVehicles()
        val monthSeconds: Long = 60 * 60 * 24 * 30
        val millisInSecond: Long = 1000
        val expirationDate = Date(month.time + monthSeconds * millisInSecond)

        vehicles.forEach {
            generateVehicleInvoice(it, country, month, expirationDate)
        }
    }

    fun generateVehicleInvoice(vehicle: Vehicle, country: Country, month: Date, expirationDate: Date) {
        var totalMeters = 0.0
        val rider: Profile = vehicle.owner!!

        vehicle.activities.filter {
            it.creationDate.month == month.month
            && it.creationDate.year == month.year
            && it.country.name == country.name
            && it.rider.id == rider.id
        }.map {
            totalMeters += getTotalDistanceOfActivity(it)
        }

        val invoice = Invoice(
            InvoiceGenerationType.AUTO,
            InvoiceState.OPEN,
            rider,
            vehicle,
            expirationDate,
            totalMeters / 1000
        ).apply {
            totalPrice = vehicle.rate!!.kmPrice * this.kilometers
        }

        invoiceDao.addInvoice(invoice)
    }

    fun getTotalDistanceOfActivity(activity: Activity): Double {
        var totalMeters = 0.0
        var prevLocation: Location = activity.locations.first()

        activity.locations.filter {
            !prevLocation.equals(it)
        }.map {
            totalMeters += measureGeoDistance(prevLocation, it)
            prevLocation = it
        }

        return totalMeters
    }
}
