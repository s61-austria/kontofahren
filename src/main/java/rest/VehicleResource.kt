package rest

import domain.enums.VehicleType
import service.VehicleService
import utils.Open
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("vehicles")
@Open
class VehicleResource @Inject constructor(
    private val vehicleService: VehicleService
) : BaseResource() {

    @GET
    @Produces("application/json")
    fun allVehiclesInCountry(): Response {
        val vehicles = vehicleService.allVehicles()

        return Response.ok(vehicles).build()
    }

    @GET
    @Path("/{countryName}")
    @Produces("application/json")
    fun getAllVehiclesInCountry(@PathParam("countryName") countryName: String): Response {
        val vehicles = vehicleService.getAllVehiclesInCountry(countryName)

        return Response.ok(vehicles).build()
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    fun addVehicle(): Response {
        val vehicle = vehicleService.addVehicle(
            params.getFirst("serialNumber"),
            VehicleType.valueOf(params.getFirst("vehicleType")),
            params.getFirst("licensePlate")
        )

        return Response.ok(vehicle).build()
    }

    @PUT
    @Path("/{uuid}")
    @Produces("application/json")
    fun saveVehicle(
        @PathParam("uuid") uuid: String
    ): Response {
        val vehicle = vehicleService.vehicleDao.getVehicleByUuid(uuid) ?: return Response.status(404).build()
        val licensePlate: String = params.getFirst("licensePlate") ?: return Response.notModified().build()
        val ownerId: String = params.getFirst("ownerId") ?: return Response.notModified().build()

        if (licensePlate.isEmpty()) return Response.notModified().build()
        if (ownerId.isEmpty()) return Response.notModified().build()

        val vehicle2 = vehicleService.saveVehicle(
            vehicle.uuid,
            licensePlate,
            ownerId
        )

        return Response.ok(vehicle2).build()
    }
}
