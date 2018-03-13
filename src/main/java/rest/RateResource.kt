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
class RateResource @Inject constructor(val rateService: RateService) {

    @GET
    @Produces("application/json")
    fun getAllRates() = rateService.getAllRates()

    @GET
    @Path("/vehicle/{serialNumber}")
    @Produces("application/json")
    fun getRateByVehicle(@PathParam("serialNumber") serialNumber: String): Response {
        return Response.ok(rateService.getRateById(serialNumber)).build()
    }

    @GET
    @Path("/id/{id}")
    @Produces("application/json")
    fun getRateById(@PathParam("id") rateId: String): Rate {
        return rateService.getRateById(rateId)
    }

    @POST
    @Path("/add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    fun addRate(
        @FormParam("vehicleType") vehicleType: String,
        @FormParam("kmPrice") kmPrice: Double,
        @FormParam("vignette") vignette: String
    ): Rate {
        return rateService.addRate(VehicleType.valueOf(vehicleType), kmPrice,
            VignetteType.valueOf(vignette))
    }

    @PUT
    @Path("/update")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    fun updateRate(
        @FormParam("rateId") rateId: String,
        @FormParam("vehicleType") vehicleType: String,
        @FormParam("kmPrice") kmPrice: Double,
        @FormParam("vignette") vignette: String
    ): Rate {
        return rateService.updateRate(rateId, VehicleType.valueOf(vehicleType), kmPrice,
            VignetteType.valueOf(vignette))
    }
}
