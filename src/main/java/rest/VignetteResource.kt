package rest

import domain.Rate
import domain.Vignette
import domain.enums.VehicleType
import domain.enums.VignetteType
import service.VignetteService

import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("vignettes")
class VignetteResource @Inject constructor(val vignetteService: VignetteService){

    @GET
    @Produces("application/json")
    fun getAllVignettes() = vignetteService.getAllVignettes()

    @POST
    @Path("/add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    fun addVignette(@FormParam("vehicleType") vehicleType: String,
                @FormParam("vignetteType") vignetteType: String,
                @FormParam("price") price: Double): Vignette {
        return vignetteService.addVignette(VehicleType.valueOf(vehicleType), VignetteType.valueOf(vignetteType), price)
    }


}
