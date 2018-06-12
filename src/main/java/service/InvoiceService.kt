package service

import com.s61.integration.model.InternationalInvoice
import dao.InvoiceDao
import dao.UserDao
import domain.Country
import domain.Invoice
import domain.Point
import domain.Vehicle
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceGenerationType.AUTO
import domain.enums.InvoiceState
import org.joda.time.DateTime
import singletons.EuropeanIntegration
import java.util.Date
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class InvoiceService @Inject constructor(
    val invoiceDao: InvoiceDao,
    val userDao: UserDao,
    val vehicleService: VehicleService,
    val europeanIntegration: EuropeanIntegration
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
            invoice.createdFor, DateTime(invoice.expires)) ?: return null

        invoice.meters = newInvoice.meters
        invoice.totalPrice = newInvoice.totalPrice

        invoiceDao.updateInvoice(invoice)

        return invoice
    }

    fun generateVehiclesInvoices(country: Country, month: Date): List<Invoice> = vehicleService
        .allVehicles().mapNotNull { generateVehicleInvoice(it, country, month) }

    fun generateVehicleInvoice(
        vehicle: Vehicle,
        country: Country,
        month: Date,
        expirationDate: DateTime = DateTime.now().plusMonths(1)
    ): Invoice? {
        val dateMonth = DateTime(month)

        val points = vehicle.locations
            .filter {
                val date = DateTime(it.creationDate)
                date.monthOfYear == dateMonth.monthOfYear
            }
            .map { it.point }

        val distance = distance(points)

        val invoice = Invoice(
            InvoiceGenerationType.AUTO,
            InvoiceState.OPEN,
            expirationDate.toDate(),
            month,
            distance
        ).apply {
            this.totalPrice = vehicle.rate.kmPrice * distance
            this.country = country
            this.vehicle = vehicle
        }

        invoiceDao.addInvoice(invoice)

        europeanIntegration.connection.publishInvoice(InternationalInvoice(
            "AT-${invoice.vehicle.licensePlate}",
            invoice.totalPrice,
            distance,
            invoice.expires,
            invoice.createdOn
        ))

        return invoice
    }

    fun saveForeignInvoice(foreignInvoice: InternationalInvoice) {
        val invoice = Invoice(AUTO)

        // If it's our license plate, skip it
        if (foreignInvoice.licencePlate.startsWith("AT")) return

        val vehicle = vehicleService.getVehicleByPlate(foreignInvoice.licencePlate)

        invoice.createdOn = foreignInvoice.createdDate
        invoice.totalPrice = foreignInvoice.price
        invoice.expires = foreignInvoice.dueByDate
        invoice.meters = foreignInvoice.distance
        invoice.vehicle = vehicle

        invoiceDao.addInvoice(invoice)
    }

    fun distance(points: List<Point>) = points.zip(points.drop(1))
        .map { it.first.distanceBetween(it.second) }
        .fold(0.0) { acc, d -> acc + d }
}
