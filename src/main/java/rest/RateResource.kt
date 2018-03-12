package rest

import domain.Rate
import domain.enums.VehicleType
import domain.enums.VignetteType
import service.RateService

import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("rates")
class RateResource @Inject constructor(val rateService: RateService){

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
    fun addRate(@FormParam("vehicleType") vehicleType: String,
                @FormParam("kmPrice") kmPrice: Double,
                @FormParam("vignette") vignette: String): Rate {
        return rateService.addRate(VehicleType.valueOf(vehicleType), kmPrice, VignetteType.valueOf(vignette))
    }

    @POST
    @Path("/update")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    fun updateRate(@FormParam("rateId") rateId: String,
                   @FormParam("vehicleType") vehicleType: String,
                   @FormParam("kmPrice") kmPrice: Double,
                   @FormParam("startPrice") startPrice: Double): Rate {
        return rateService.updateRate(rateId, VehicleType.valueOf(vehicleType), kmPrice, startPrice)
    }


}