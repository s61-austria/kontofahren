package rest

import domain.enums.VehicleType
import service.VehicleService
import utils.Open
import utils.decode
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("vehicles")
@Open
class VehicleResource @Inject constructor(
    private val vehicleService: VehicleService
) {
    @Context
    private lateinit var request: UriInfo

    @GET
    @Produces("application/json")
    fun getAllVehicles(
        @QueryParam("stolen") stolen: String?
    ): Response {
        val vehicles = if (stolen == "true") {
            vehicleService.getStolenVehicle()
        } else {
            vehicleService.allVehicles()
        }

        return Response.ok(vehicles).build()
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    fun addVehicle(): Response {
        val vehicle = vehicleService.addVehicle(
            request.queryParameters.getFirst("serialNumber"),
            VehicleType.valueOf(request.queryParameters.getFirst("vehicleType")),
            request.queryParameters.getFirst("licensePlate")
        )

        return Response.ok(vehicle).build()
    }

    @PUT
    @Produces("application/json")
    fun saveVehicle(body: String): Response {
        val vehicle = decode(body, mutableMapOf<String, String>()::class.java)

        if (vehicle["uuid"] == null ||
            vehicle["uuid"]!!.isBlank()) return Response.notModified().build()

        if (vehicle["licensePlate"] == null ||
            vehicle["licensePlate"]!!.isBlank()) return Response.notModified().build()

        if (vehicle["owner"] == null ||
            vehicle["owner"]!!.isBlank()) return Response.notModified().build()

        val vehicle2 = vehicleService.saveVehicle(
            vehicle["uuid"]!!,
            vehicle["licensePlate"]!!,
            vehicle["owner"]!!
        ) ?: return Response.notModified().build()

        return Response.ok(vehicle2).build()
    }
}
