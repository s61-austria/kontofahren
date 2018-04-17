package rest

import service.InvoiceService
import service.PaymentService
import utils.Open
import javax.inject.Inject
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("payments")
@Open
class PaymentResource @Inject constructor(
    private val paymentService: PaymentService
) {

    @PUT
    @Path("/create/{uuid}")
    @Produces("application/json")
    fun createPayment(@PathParam("uuid") uuid: String): Response {
        val regeneratedInvoice = paymentService.createPayment(uuid)
            ?: return Response.notModified().build()

        return Response.ok(regeneratedInvoice).build()
    }
}
