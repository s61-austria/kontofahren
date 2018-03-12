package service

import dao.UserDao
import dao.VehicleDao
import domain.*
import domain.Location
import domain.Vehicle
import domain.enums.VehicleType
import exceptions.KontoException

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class VehicleService @Inject constructor(
        val vehicleDao: VehicleDao,
        val userDao: UserDao
) {

    fun allVehicles(): List<Vehicle> = vehicleDao.allVehicles()

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> =
            vehicleDao.getAllVehiclesInCountry(countryName)

    fun addVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, licensePlate: String): Vehicle =
            vehicleDao.persistVehicle(Vehicle(hardwareSerialNumber, vehicleType, Location(), licensePlate))

    fun saveVehicle(id: Long, licensePlate: String, newOwnerId: Long): Vehicle {

        val vehicle = vehicleDao.getVehicleById(id)
        //if licenseplate changed
        if (vehicle.licensePlate !== licensePlate) {
            vehicle.licensePlate = licensePlate
        }
        //change owner if changed.
        val prevOwner: Profile = vehicle.owner ?: return vehicle

        if (prevOwner.id != newOwnerId) {
            val newOwner = userDao.getUserById(newOwnerId).profile ?: throw KontoException("KEIN OWNER")
            try {
                prevOwner.removeVehicle(vehicle)
                newOwner.addVehicle(vehicle)
                vehicle.owner = newOwner

            } catch (e: KontoException) {
                e.printStackTrace()
            }

        }
        return vehicleDao.persistVehicle(vehicle)
    }
}
