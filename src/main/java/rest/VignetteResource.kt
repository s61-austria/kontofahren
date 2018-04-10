package rest

import domain.Vignette
import domain.enums.VehicleType
import domain.enums.VignetteType
import service.VignetteService
import utils.Open
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo

@Path("vignettes")
@Open
class VignetteResource @Inject constructor(private val vignetteService: VignetteService) {
    @Context
    private lateinit var request: UriInfo

    @GET
    @Produces("application/json")
    fun getAllVignettes() = vignetteService.getAllVignettes()

    @POST
    @Path("/add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    fun addVignette(
        @FormParam("vehicleType") vehicleType: String,
        @FormParam("vignetteType") vignetteType: String,
        @FormParam("price") price: Double
    ): Vignette {
        return vignetteService.addVignette(VehicleType.valueOf(vehicleType), VignetteType.valueOf(vignetteType), price)
    }
}
