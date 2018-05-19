package service

import dao.ProfileDao
import dao.UserDao
import dao.VehicleDao
import domain.Profile
import domain.Vehicle
import domain.enums.VehicleType
import utils.Open
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
@Open
class VehicleService @Inject constructor(
    val vehicleDao: VehicleDao,
    val userDao: UserDao,
    val profileDao: ProfileDao
) {
    fun allVehicles(): List<Vehicle> = vehicleDao.allVehicles()

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> =
        vehicleDao.getAllVehiclesInCountry(countryName)

    fun addVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, licensePlate: String): Vehicle =
        vehicleDao.persistVehicle(Vehicle(
            hardwareSerialNumber, vehicleType = vehicleType, licensePlate = licensePlate))

    fun saveVehicle(uuid: String, licensePlate: String, newOwnerId: String): Vehicle? {
        val vehicle = vehicleDao.getVehicleByUuid(uuid) ?: return null

        vehicle.licensePlate = licensePlate

        //change owner if changed.
        val prevOwner: Profile? = vehicle.owner

        if (prevOwner == null || !prevOwner.id.equals(newOwnerId)) {
            val newOwner: Profile = profileDao.getProfileByUuid(newOwnerId)
                ?: return vehicleDao.persistVehicle(vehicle)

            newOwner.addVehicle(vehicle)
            vehicle.owner = newOwner
            profileDao.persist(newOwner)

            if (prevOwner != null) {
                prevOwner.removeVehicle(vehicle)
                vehicle.addPastOwner(prevOwner)
                profileDao.persist(prevOwner)
            }
        }

        return vehicleDao.persistVehicle(vehicle)
    }

    fun getVehicleByUuid(uuid: String) = vehicleDao.getVehicleByUuid(uuid)
    fun getStolenVehicle() = vehicleDao.getStolenVehicle()

    fun updateVehicle(vehicle: Vehicle) = vehicleDao.merge(vehicle)
}
