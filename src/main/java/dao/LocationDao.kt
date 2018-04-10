package dao

import domain.Location
import utils.Open
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Open
@Stateless
class LocationDao {
    @PersistenceContext
    lateinit var em: EntityManager

    fun createLocation(location: Location) = em.persist(location)
}
