package service

import dao.VehicleDao
import domain.Location
import domain.Vehicle
import domain.enums.VehicleType

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class VehicleService @Inject constructor(
        val vehicleDao: VehicleDao
){
    val allVehicles: List<Vehicle>
        get() = vehicleDao.allVehicles

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> {
        return vehicleDao.getAllVehiclesInCountry(countryName)
    }

    fun addVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, licensePlate: String): Vehicle {
        return vehicleDao!!.addVehicle(Vehicle(hardwareSerialNumber, vehicleType, Location(), licensePlate))
    }
}
