package rest;

import domain.Vehicle;
import domain.enums.VehicleType;
import service.VehicleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("vehicles")
@Stateless
public class VehicleResource {

    @Inject
    VehicleService vehicleService;

    @GET
    @Path("/{countryName}")
    @Produces("application/json")
    public List<Vehicle> getAllVehiclesInCountry(@PathParam("countryName") String countryName) {
        return vehicleService.getAllVehiclesInCountry(countryName);
    }

    @POST
    @Path("/add/{serialNumber}/{vehicleType}")
    @Produces("application/json")
    public Vehicle addVehicle(@PathParam("serialNumber") String serialNumber,
                              @PathParam("vehicleType") String vehicleType) {
        return vehicleService.addVehicle(serialNumber, VehicleType.valueOf(vehicleType));
    }
}
