package rest

import domain.Vehicle
import domain.enums.VehicleType
import service.VehicleService

import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.*
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
    fun addVehicle(@PathParam("serialNumber") serialNumber: String,
                   @PathParam("vehicleType") vehicleType: String,
                   @PathParam("plate") licensePlate: String): Response {

        return Response.ok(vehicleService.addVehicle(serialNumber, VehicleType.valueOf(vehicleType), licensePlate)).build()
    }
    @POST
    @Path("/save/{id}/{licensePlate}/{ownerId}")
    @Produces("application/json")
    fun saveVehicle(@PathParam("id") id: Long,
                    @PathParam("licensePlate") licensePlate: String,
                    @PathParam("ownerId") ownerId: Long): Vehicle {
        return vehicleService.saveVehicle(id, licensePlate, ownerId)
    }
}
