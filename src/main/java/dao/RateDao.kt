package dao

import domain.Rate
import domain.Vehicle

import javax.ejb.Stateless
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery

@Stateless
class RateDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getAllRates() = em
            .createQuery("SELECT r FROM Rate r", Rate::class.java)
            .resultList

    fun getRateById(rateId: String) = em
            .createQuery("SELECT r FROM Rate r WHERE r.id = :rateId", Rate::class.java)
            .setParameter("rateId", rateId)
            .singleResult

    fun getRateByVehicle(serialNumber: String): Rate {
        return Rate()
    }

    fun addRate(rate: Rate): Rate {
        em.persist(rate)
        return rate
    }

    fun updateRate(rate: Rate) {
        em.merge(rate)
    }

}