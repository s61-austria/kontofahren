package singletons

import com.google.gson.Gson
import com.kontofahren.integrationslosung.Queue
import com.kontofahren.integrationslosung.RabbitGateway
import dao.InvoiceDao
import domain.Invoice
import domain.Point
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import logger
import org.joda.time.DateTime
import serializers.InvoiceGenerateSerializer
import service.CountryService
import service.InvoiceService
import service.VehicleService
import utils.Open
import java.util.Date
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Open
@Startup
@Singleton
class InvoiceGenerator @Inject constructor(
    val invoiceService: InvoiceService,
    val vehicleService: VehicleService,
    val invoiceDao: InvoiceDao,
    val countryService: CountryService
) {

    val rabbitGateway by lazy { RabbitGateway() }

    @PostConstruct
    fun setup() {
        logger.info("Setting up Invoice Generator")
        rabbitGateway.consume(Queue.INVOICE_GENERATION, { generateInvoice(it) })
    }

    fun generateInvoice(body: String) {
        val decoded = Gson().fromJson(body, InvoiceGenerateSerializer::class.java)

        if (decoded.invoiceUuid == null) {

            logger.info("Received request to generate invoice")
            addInvoice(decoded)
        } else {

            logger.info("Received request to regenerate invoice")
            val originalInvoice = invoiceService.getInvoiceByUuid(decoded.invoiceUuid!!) ?: throw Exception("Invoice was not found")
            regenerateInvoice(originalInvoice)
        }
    }

    fun addInvoice(decoded: InvoiceGenerateSerializer) {
        val vehicle = vehicleService.getVehicleByUuid(decoded.vehicleUuid!!) ?: throw Exception("Vehicle is not found")
        val country = countryService.getCountryByUUID(decoded.countryUuid!!) ?: throw Exception("Country is not found")
        val month = decoded.month
        val expirationDate = decoded.expirationDate

        val dateMonth = DateTime(month)

        val points = vehicle.locations
            .filter {
                val date = DateTime(it.creationDate)
                date.monthOfYear() == dateMonth.monthOfYear()
            }
            .map { it.point }

        val distance = distance(points)

        val invoice = Invoice(
            InvoiceGenerationType.AUTO,
            InvoiceState.OPEN,
            Date(expirationDate),
            Date(month),
            distance
        ).apply {
            this.totalPrice = vehicle.rate.kmPrice * (distance / 1000)
            this.country = country
            this.vehicle = vehicle
        }

        invoiceDao.addInvoice(invoice)
    }

    fun regenerateInvoice(invoice: Invoice) {

        val vehicle = invoice.vehicle
        val country = invoice.country
        val month = invoice.createdFor
        val expirationDate = DateTime(invoice.expires)

        val dateMonth = DateTime(month)

        val points = invoice.vehicle.locations
            .filter {
                val date = DateTime(it.creationDate)
                date.monthOfYear() == dateMonth.monthOfYear()
            }
            .map { it.point }

        val distance = distance(points)

        val newInvoice = Invoice(
            InvoiceGenerationType.AUTO,
            InvoiceState.OPEN,
            expirationDate.toDate(),
            month,
            distance
        ).apply {
            this.totalPrice = vehicle.rate.kmPrice * (distance / 1000)
            this.country = country
            this.vehicle = vehicle
        }

        invoice.meters = newInvoice.meters
        invoice.totalPrice = newInvoice.totalPrice
        invoice.generationType = InvoiceGenerationType.MANUAL

        invoiceDao.updateInvoice(invoice)
    }

    fun distance(points: List<Point>) = points.zip(points.drop(1))
        .map { it.first.distanceBetween(it.second) }
        .fold(0.0) { acc, d -> acc + d }
}
