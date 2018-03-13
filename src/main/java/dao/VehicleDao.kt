package dao

import domain.Vehicle

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class VehicleDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun allVehicles(): List<Vehicle> = em.createQuery("SELECT v FROM Vehicle v", Vehicle::class.java).resultList

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> =
        em.createQuery("SELECT v FROM Vehicle v JOIN v.currentLocation l JOIN l.country c WHERE c.name = :countryName", Vehicle::class.java)
            .setParameter("countryName", countryName)
            .resultList

    fun getVehicleById(id: String) = em.createQuery<Vehicle>("SELECT v FROM Vehicle v WHERE v.id = :id", Vehicle::class.java)
        .singleResult


    fun persistVehicle(vehicle: Vehicle): Vehicle {
        em!!.persist(vehicle)

        return vehicle
    }
}
