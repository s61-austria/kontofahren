package service;

import dao.UserDao;
import dao.VehicleDao;
import domain.Civilian;
import domain.Location;
import domain.User;
import domain.Vehicle;
import domain.enums.VehicleType;
import exceptions.KontoException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class VehicleService {

    @Inject
    VehicleDao vehicleDao;
    @Inject
    UserDao userDao;

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.getAllVehicles();
    }

    public List<Vehicle> getAllVehiclesInCountry(String countryName) {
        return vehicleDao.getAllVehiclesInCountry(countryName);
    }

    public Vehicle addVehicle(String hardwareSerialNumber, VehicleType vehicleType) {
        return vehicleDao.persistVehicle(new Vehicle(hardwareSerialNumber, vehicleType, new Location()));
    }

    public Vehicle saveVehicle(long id, String licensePlate, long newOwnerId){

        Vehicle vehicle = vehicleDao.getVehicleById(id);
        //if licenseplate changed
        if(vehicle.getLicensePlate() != licensePlate){
            vehicle.setLicensePlate(licensePlate);
        }
        //change owner if changed.
        Civilian prevOwner = vehicle.getOwner();
        if(prevOwner.getId() != newOwnerId){
            Civilian newOwner = (Civilian) userDao.getUserById(newOwnerId);
            try {
                prevOwner.removeVehicle(vehicle);
                newOwner.addVehicle(vehicle);
                vehicle.setOwner(newOwner);

            } catch (KontoException e) {
                e.printStackTrace();
            }
        }
        return vehicleDao.persistVehicle(vehicle);
    }
}
