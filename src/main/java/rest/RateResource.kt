package rest

import domain.Rate
import service.RateService
import utils.Open
import utils.decode
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("rates")
@Open
class RateResource @Inject constructor(private val rateService: RateService) {
    @Context
    private lateinit var request: UriInfo

    @GET
    @Produces("application/json")
    fun getAllRates(): Response {
        return Response.ok(rateService.getAllRates()).build()
    }

    @GET
    @Path("/{uuid}")
    @Produces("application/json")
    fun getRateById(@PathParam("uuid") rateId: String): Response {
        return Response.ok(rateService.getRateByUuid(rateId)).build()
    }

    @POST
    @Produces("application/json")
    fun addRate(message: String): Response {
        val rateObject: Rate = decode(message, Rate::class.java)

        val rate: Rate = rateService.addRate(
            rateObject.vehicleType,
            rateObject.kmPrice,
            rateObject.vignetteType
        )

        return Response.ok(rate).build()
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    fun updateRate(
        @PathParam("id") id: String,
        message: String
    ): Response {
        val rateObject: Rate = decode(message, Rate::class.java)

        val rate = rateService.updateRate(
            id,
            rateObject.vehicleType,
            rateObject.kmPrice,
            rateObject.vignetteType
        )

        return Response.ok(rate).build()
    }
}
