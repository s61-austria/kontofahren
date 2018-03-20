package service

import dao.UserDao
import dao.VehicleDao
import domain.Profile
import domain.Vehicle
import domain.enums.VehicleType
import exceptions.KontoException
import utils.Open
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
@Open
class VehicleService @Inject constructor(
    val vehicleDao: VehicleDao,
    val userDao: UserDao
) {
    fun allVehicles(): List<Vehicle> = vehicleDao.allVehicles()

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> =
        vehicleDao.getAllVehiclesInCountry(countryName)

    fun addVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, licensePlate: String): Vehicle =
        vehicleDao.persistVehicle(Vehicle(
            hardwareSerialNumber, vehicleType = vehicleType, licensePlate = licensePlate))

    fun saveVehicle(uuid: String, licensePlate: String, newOwnerId: String): Vehicle {

        val vehicle = vehicleDao.getVehicleByUuid(uuid)
        //if licenseplate changed
        if (vehicle.licensePlate !== licensePlate) {
            vehicle.licensePlate = licensePlate
        }
        //change owner if changed.
        val prevOwner: Profile = vehicle.owner ?: return vehicle

        if (prevOwner.id.equals(newOwnerId)) {
            val newOwner = userDao.getUserByUuid(newOwnerId)?.profile ?: throw KontoException("KEIN OWNER")
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
