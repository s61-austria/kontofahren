package service

import com.s61.integration.model.Countries
import com.s61.integration.model.InternationalCar
import dao.ProfileDao
import dao.UserDao
import dao.VehicleDao
import domain.Profile
import domain.Rate
import domain.Vehicle
import domain.enums.VehicleType
import domain.enums.VignetteType
import singletons.EuropeanIntegrationPublisher
import utils.Open
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
@Open
class VehicleService @Inject constructor(
    val vehicleDao: VehicleDao,
    val userDao: UserDao,
    val profileDao: ProfileDao,
    private val europeanIntegration: EuropeanIntegrationPublisher
) {
    fun allVehicles(): List<Vehicle> = vehicleDao.allVehicles()

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> =
        vehicleDao.getAllVehiclesInCountry(countryName)

    fun addAustrianVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, licensePlate: String): Vehicle {
        val vehicle: Vehicle = addVehicle(hardwareSerialNumber, vehicleType, licensePlate)

        europeanIntegration.publishCar(
            InternationalCar("AT-${vehicle.licensePlate}", Countries.AUSTRIA, false)
        )

        return vehicle
    }

    fun addVehicle(hardwareSerialNumber: String, vehicleType: VehicleType, licensePlate: String): Vehicle {
        val vehicle = vehicleDao.persistVehicle(Vehicle(
            hardwareSerialNumber, vehicleType = vehicleType, licensePlate = licensePlate).apply {
            rate = Rate(vehicleType, VignetteType.TEN_DAYS, 0.0)
        })

        return vehicle
    }

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

    fun getVehicleByPlate(plate: String) = vehicleDao.getVehicleByPlate(plate)
}
