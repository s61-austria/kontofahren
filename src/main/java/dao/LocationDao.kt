package dao

import domain.Location
import logger
import utils.Open
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Open
@Stateless
class LocationDao {
    @PersistenceContext
    lateinit var em: EntityManager

    fun createLocation(location: Location) {
        em.merge(location)
        em.merge(location.point)
    }
    fun updateLocation(location: Location) = em.merge(location)
    fun getLocation(locationId: String): Location? = try {
        em.createQuery("select l from Location l WHERE l.uuid LIKE :uuid", Location::class.java)
            .setParameter("uuid", locationId)
            .singleResult
    } catch (ex: Exception) {
        logger.warn("Failed to retrieve location for id $locationId")
        null
    }
}
