package dao

import domain.Vehicle

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery

@Stateless
class VehicleDao {

    @PersistenceContext
    internal var em: EntityManager? = null

    val allVehicles: List<Vehicle>
        get() {
            val query = em!!.createQuery<Vehicle>("SELECT v FROM Vehicle v", Vehicle::class.java)

            return query.resultList
        }

    fun getAllVehiclesInCountry(countryName: String): List<Vehicle> {
        val query = em!!.createQuery<Vehicle>("SELECT v FROM Vehicle v " + "JOIN v.currentLocation l JOIN l.country c WHERE c.name = :countryName", Vehicle::class.java)

        return query.setParameter("countryName", countryName).resultList
    }

    fun getVehicleById(id: Long): Vehicle {
        val query = em!!.createQuery<Vehicle>("SELECT v FROM Vehicle v WHERE v.id = :id", Vehicle::class.java)
        return query.singleResult
    }

    fun persistVehicle(vehicle: Vehicle): Vehicle {
        em!!.persist(vehicle)

        return vehicle
    }
}
