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

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.getAllVehicles();
    }

    public List<Vehicle> getAllVehiclesInCountry(String countryName) {
        return vehicleDao.getAllVehiclesInCountry(countryName);
    }

    public Vehicle addVehicle(String hardwareSerialNumber, VehicleType vehicleType,
                              String licensePlate) {
        return vehicleDao.addVehicle(new Vehicle(hardwareSerialNumber, vehicleType,
                new Location(), licensePlate));
    }
}
