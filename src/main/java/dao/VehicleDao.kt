package dao

import domain.Vehicle

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class VehicleDao {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun allVehicles(): List<Vehicle> = entityManager.createQuery(
        "SELECT v FROM Vehicle v", Vehicle::class.java).resultList

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> =
        entityManager.createQuery(
            "SELECT v FROM Vehicle v JOIN v.currentLocation l " +
                "JOIN l.country c WHERE c.name = :countryName", Vehicle::class.java)
            .setParameter("countryName", countryName)
            .resultList

    fun getVehicleByUuid(uuid: String): Vehicle? = entityManager.createQuery<Vehicle>(
        "SELECT v FROM Vehicle v WHERE v.uuid = :uuid", Vehicle::class.java)
        .setParameter("uuid", uuid)
        .singleResult

    fun persistVehicle(vehicle: Vehicle): Vehicle {
        entityManager.persist(vehicle)

        return vehicle
    }
}
