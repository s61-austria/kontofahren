package rest

import domain.Rate
import domain.enums.VehicleType
import domain.enums.VignetteType
import service.RateService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("rates")
class RateResource @Inject constructor(val rateService: RateService) : BaseResource() {

    @GET
    @Produces("application/json")
    fun getAllRates() : Response {
        return Response.ok(rateService.getAllRates()).build()
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    fun getRateById(@PathParam("id") rateId: String): Response {
        return Response.ok(rateService.getRateById(rateId)).build()
    }

    @POST
    @Produces("application/json")
    fun addRate(): Response {
        val vehicle = rateService.addRate(
            VehicleType.valueOf( params.getFirst("vehicleType")),
            params.getFirst("kmPrice").toDouble(),
            VignetteType.valueOf(params.getFirst("vignette"))
        )

        return Response.ok(vehicle).build()
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    fun updateRate(
        @PathParam("id") id: String
    ): Response {
        val vehicle = rateService.updateRate(
            id,
            VehicleType.valueOf(params.getFirst("vehicleType")),
            params.getFirst("kmPrice").toDouble(),
            VignetteType.valueOf(params.getFirst("vignette"))
        )

        return Response.ok(vehicle).build()
    }
}
