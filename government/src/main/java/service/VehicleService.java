package service;

import dao.VehicleDao;
import domain.Location;
import domain.Vehicle;
import domain.enums.VehicleType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class VehicleService {

    @Inject
    VehicleDao vehicleDao;

    public List<Vehicle> getAllVehiclesInCountry(String countryName) {
        return vehicleDao.GetAllVehiclesInCountry(countryName);
    }

    public Vehicle addVehicle(String hardwareSerialNumber, VehicleType vehicleType) {
        return vehicleDao.addVehicle(new Vehicle(hardwareSerialNumber, vehicleType, new Location()));
    }
}
