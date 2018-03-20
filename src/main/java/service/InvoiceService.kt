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

    fun generateInvoiceForVehicle(vehicle: Vehicle, country: Country, rider: Profile,
                                  month: Date, expirationDate: Date) {
        var totalMeters = 0.0

        vehicle.activities.filter {
            it.creationDate.month == month.month
            && it.creationDate.year == month.year
            && it.country.name == country.name
            && it.rider.id == rider.id
        }.map {
            totalMeters += getTotalDistanceOfActivity(it)
        }

        var invoice = Invoice(InvoiceGenerationType.AUTO, rider, vehicle,
            expirationDate, totalMeters)

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
