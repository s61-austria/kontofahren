package rest

import domain.Vehicle
import domain.enums.VehicleType
import service.VehicleService
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("vehicles")
class VehicleResource @Inject constructor(
    val vehicleService: VehicleService
) : BaseResource() {

    @GET
    @Produces("application/json")
    fun allVehiclesInCountry(): Response {
        return Response.ok(vehicleService.allVehicles()).build()
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
    fun addVehicle(): Response {
        val vehicle = vehicleService.addVehicle(
            params.getFirst("serialNumber"),
            VehicleType.valueOf(params.getFirst("vehicleType")),
            params.getFirst("licensePlate")
        )

        return Response.ok(vehicle).build()
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    fun saveVehicle(
        @PathParam("id") id: String
    ): Response {
        val vehicle = vehicleService.vehicleDao.getVehicleById(id) ?: return Response.status(404).build()
        val licensePlate:String = params.getFirst("licensePlate") ?: return Response.notModified().build()
        val ownerId:String = params.getFirst("ownerId") ?: return Response.notModified().build()

        if (licensePlate.isEmpty()) return Response.notModified().build()
        if (ownerId.isEmpty()) return Response.notModified().build()

        val vehicle2 = vehicleService.saveVehicle(
            vehicle.id,
            licensePlate,
            ownerId
        )

        return Response.ok(vehicle2).build()
    }
}
