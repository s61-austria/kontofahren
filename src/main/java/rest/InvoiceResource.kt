package rest

import domain.Invoice
import service.InvoiceService

import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("invoices")
class InvoiceResource @Inject constructor(
        val invoiceService: InvoiceService
) {

    @GET
    @Produces("application/json")
    fun allInvoices() = invoiceService.allInvoices

    @GET
    @Path("{id}")
    @Produces("application/json")
    fun getInvoiceById(@PathParam("id") id: Long?): Response {
        return Response.ok().build()
    }
}
