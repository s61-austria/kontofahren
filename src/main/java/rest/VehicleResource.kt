package rest

import domain.Vehicle
import domain.enums.VehicleType
import service.VehicleService

import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.*

@Path("vehicles")
@Stateless
class VehicleResource {

    @Inject
    internal var vehicleService: VehicleService? = null

    val allVehiclesInCountry: List<Vehicle>
        @GET
        @Produces("application/json")
        get() = vehicleService!!.allVehicles

    @GET
    @Path("/{countryName}")
    @Produces("application/json")
    fun getAllVehiclesInCountry(@PathParam("countryName") countryName: String): List<Vehicle> {
        return vehicleService!!.getAllVehiclesInCountry(countryName)
    }

    @POST
    @Path("/add/{serialNumber}/{vehicleType}/{plate}")
    @Produces("application/json")
    fun addVehicle(@PathParam("serialNumber") serialNumber: String,
                   @PathParam("vehicleType") vehicleType: String,
                   @PathParam("plate") plate: String): Vehicle {
        return vehicleService!!.addVehicle(serialNumber, VehicleType.valueOf(vehicleType), plate)
    }

    @POST
    @Path("/save/{id}/{licensePlate}/{ownerId}")
    @Produces("application/json")
    fun saveVehicle(@PathParam("id") id: Long?,
                    @PathParam("licensePlate") licensePlate: String,
                    @PathParam("ownerId") ownerId: Long): Vehicle {
        return vehicleService!!.saveVehicle(id!!, licensePlate, ownerId)
    }
}
