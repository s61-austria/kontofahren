package service

import dao.UserDao
import dao.VehicleDao
import domain.*
import domain.Civilian
import domain.Location
import domain.Vehicle
import domain.enums.VehicleType
import exceptions.KontoException

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class VehicleService {

    @Inject
    internal var vehicleDao: VehicleDao? = null
    @Inject
    internal var userDao: UserDao? = null

    val allVehicles: List<Vehicle>
        get() = vehicleDao!!.allVehicles

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> {
        return vehicleDao!!.getAllVehiclesInCountry(countryName)
    }

    fun addVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, plate: String): Vehicle {

        return vehicleDao!!.persistVehicle(Vehicle(hardwareSerialNumber, vehicleType, Location()))
    }

    fun saveVehicle(id: Long, licensePlate: String, newOwnerId: Long): Vehicle {

        val vehicle = vehicleDao!!.getVehicleById(id)
        //if licenseplate changed
        if (vehicle.licensePlate !== licensePlate) {
            vehicle.licensePlate = licensePlate
        }
        //change owner if changed.
        val prevOwner = vehicle.owner
        if (prevOwner!!.id != newOwnerId) {
            val newOwner = userDao!!.getUserById(newOwnerId).profile
            try {
                prevOwner.removeVehicle(vehicle)
                newOwner!!.addVehicle(vehicle)
                vehicle.owner = newOwner

            } catch (e: KontoException) {
                e.printStackTrace()
            }

        }
        return vehicleDao!!.persistVehicle(vehicle)
    }
}
