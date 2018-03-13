package rest

import domain.Vehicle
import domain.enums.VehicleType
import service.VehicleService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("vehicles")
class VehicleResource @Inject constructor(
    val vehicleService: VehicleService
) {

    @GET
    @Produces("application/json")
    fun allVehiclesInCountry(): List<Vehicle> = vehicleService.allVehicles()

    @GET
    @Path("/{countryName}")
    @Produces("application/json")
    fun getAllVehiclesInCountry(@PathParam("countryName") countryName: String): List<Vehicle> =
        vehicleService.getAllVehiclesInCountry(countryName)

    @POST
    @Path("/add/{serialNumber}/{vehicleType}/{plate}")
    @Produces("application/json")
    fun addVehicle(
        @PathParam("serialNumber") serialNumber: String,
        @PathParam("vehicleType") vehicleType: String,
        @PathParam("plate") licensePlate: String
    ): Response {

        return Response.ok(vehicleService.addVehicle(serialNumber, VehicleType.valueOf(vehicleType), licensePlate)).build()
    }

    @POST
    @Path("/save/{id}/{licensePlate}/{ownerId}")
    @Produces("application/json")
    fun saveVehicle(
        @PathParam("id") id: Long,
        @PathParam("licensePlate") licensePlate: String,
        @PathParam("ownerId") ownerId: String
    ): Vehicle {
        return vehicleService.saveVehicle(id, licensePlate, ownerId)
    }
}
