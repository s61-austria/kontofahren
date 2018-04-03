package rest

import domain.Invoice
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import service.InvoiceService
import java.time.Instant
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("invoices")
class InvoiceResource @Inject constructor(
    val invoiceService: InvoiceService
) : BaseResource() {

    @GET
    @Produces("application/json")
    fun allInvoices(): List<Invoice> {
        val startDate: Long = params.getFirst("startDate").toLongOrNull() ?: Instant.MIN.toEpochMilli()
        val endDate: Long = params.getFirst("endDate").toLongOrNull() ?: Instant.MAX.toEpochMilli()

        return invoiceService.allInvoicesCreatedBetweenDates(startDate, endDate)
    }

    @GET
    @Path("{uuid}")
    @Produces("application/json")
    fun getInvoiceById(@PathParam("uuid") uuid: String): Response =
        Response.ok(invoiceService.getInvoiceByUuid(uuid)).build()

    @GET
    @Path("/vehicle/{id}")
    @Produces("application/json")
    fun allInvoicesByVehicleId(@PathParam("id") id: String): List<Invoice> =
        invoiceService.allInvoicesByVehicle(id)

    @GET
    @Path("/civilian/{id}")
    @Produces("application/json")
    fun allInvoicesByCivilianId(@PathParam("id") id: String): List<Invoice> =
        invoiceService.allInvoicesByCivilian(id)

    @GET
    @Path("/date/for/{start}/{end}")
    @Produces("application/json")
    fun allInvoicesForBetweenDates(
        @PathParam("start") start: String,
        @PathParam("end") end: String
    ): List<Invoice> =
        invoiceService.allInvoicesForBetweenDates(start, end)

    @GET
    @Path("/generated/{type}")
    @Produces("application/json")
    fun allInvoicesGeneratedBy(@PathParam("type") type: String): List<Invoice> =
        invoiceService.allInvoicesGeneratedBy(InvoiceGenerationType.valueOf(type))

    @GET
    @Path("/state/{state}")
    @Produces("application/json")
    fun allInvoicesByState(@PathParam("state") state: String): List<Invoice> =
        invoiceService.allInvoicesByState(InvoiceState.valueOf(state))

    @POST
    @Path("/update/state/{uuid}")
    @Produces("application/json")
    fun updateInvoiceState(@PathParam("uuid") uuid: String): Response {
        val invoice = invoiceService.updateInvoiceState(uuid, InvoiceState.valueOf(params.getFirst("state")))
            ?: return Response.notModified().build()

        return Response.ok(invoice).build()
    }

    @PUT
    @Path("/regenerate/{uuid}")
    @Produces("application/json")
    fun regenerateInvoice(@PathParam("uuid") uuid: String): Response {
        val regeneratedInvoice = invoiceService.regenerateInvoice(uuid)
            ?: return Response.notModified().build()

        return Response.ok(regeneratedInvoice).build()
    }
}
