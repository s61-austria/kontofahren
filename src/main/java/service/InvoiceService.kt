package service

import dao.InvoiceDao
import dao.UserDao
import domain.Country
import domain.Invoice
import domain.Point
import domain.Profile
import domain.Vehicle
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import nl.stil4m.mollie.domain.Payment
import org.apache.commons.lang.time.DateUtils
import utils.MollieHelper
import java.util.Date
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class InvoiceService @Inject constructor(
    val invoiceDao: InvoiceDao,
    val userDao: UserDao,
    val vehicleService: VehicleService
) {

    fun allInvoices(): List<Invoice> = invoiceDao.allInvoices()

    fun getInvoiceByUuid(uuid: String): Invoice = invoiceDao.getInvoiceByUuid(uuid)

    fun allInvoicesByVehicle(id: String): List<Invoice> = invoiceDao.allInvoicesByVehicle(id)

    fun allInvoicesByCivilian(id: String): List<Invoice> {
        val profile = userDao.getUserByUuid(id)?.profile ?: return emptyList()
        return profile.invoices
    }

    /**
     * @param start Start filter time in millis
     * @param end End filter time in millis
     */
    fun allInvoicesCreatedBetweenDates(start: Long, end: Long): List<Invoice> = invoiceDao.allInvoicesCreatedBetweenDates(Date(start), Date(end))

    fun allInvoicesForBetweenDates(start: String, end: String): List<Invoice> = invoiceDao.allInvoicesForBetweenDates(Date(start.toLong()), Date(end.toLong()))

    fun allInvoicesGeneratedBy(type: InvoiceGenerationType): List<Invoice> = invoiceDao.allInvoicesGeneratedBy(type)

    fun allInvoicesByState(state: InvoiceState): List<Invoice> = invoiceDao.allInvoicesByStatus(state)

    fun updateInvoiceState(invoiceId: String, state: InvoiceState): Invoice? {
        val invoice = invoiceDao.getInvoiceByUuid(invoiceId)

        invoice.state = state
        invoiceDao.updateInvoice(invoice)

        return invoice
    }

    fun regenerateInvoice(uuid: String): Invoice? {
        val invoice = invoiceDao.getInvoiceByUuid(uuid)
        val newInvoice: Invoice = generateVehicleInvoice(invoice.vehicle, invoice.country,
            invoice.createdFor, invoice.expires) ?: return null

        invoice.meters = newInvoice.meters
        invoice.totalPrice = newInvoice.totalPrice
        invoice.paymentId = newInvoice.paymentId
        invoice.payLink = newInvoice.payLink

        invoiceDao.updateInvoice(invoice)

        return invoice
    }

    fun generateVehiclesInvoices(country: Country, month: Date): List<Invoice> {
        val expirationDate = DateUtils.addMonths(month, 1)

        return vehicleService
            .allVehicles()
            .map { generateVehicleInvoice(it, country, month, expirationDate) }
            .filterNotNull()
    }

    fun generateVehicleInvoice(vehicle: Vehicle, country: Country, month: Date, expirationDate: Date): Invoice? {
        var totalMeters = 0.0
        val rider: Profile = vehicle.owner ?: return null

        vehicle.activities.filter {
            it.creationDate.month == month.month
                && it.creationDate.year == month.year
                && it.country.name == country.name
                && it.rider.id == rider.id
        }.map {
            totalMeters += distance(it.locations.map { it.point })
        }

        val invoice = Invoice(
            InvoiceGenerationType.AUTO,
            InvoiceState.OPEN,
            expirationDate,
            month,
            totalMeters
        ).apply {
            this.totalPrice = vehicle.rate.kmPrice * totalMeters
            this.country = country
            this.vehicle = vehicle

            var payment = MollieHelper().createMolliePayment(totalPrice, this.uuid, month)

            if (payment != null) {
                this.payLink = payment.links.paymentUrl
                this.paymentId = payment.id
            }
        }

        invoiceDao.addInvoice(invoice)

        return invoice
    }

    fun distance(points: List<Point>) = points.zip(points.drop(1))
        .map { it.first.distanceBetween(it.second) }
        .fold(0.0) { acc, d -> acc + d }
}
