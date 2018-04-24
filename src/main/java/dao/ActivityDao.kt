package dao

import domain.Activity
import utils.Open
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
@Open
class ActivityDao {
    @PersistenceContext
    lateinit var em: EntityManager

    fun createActivity(activity: Activity) = em.persist(activity)

    fun updateActivity(activity: Activity) = em.merge(activity)
}
