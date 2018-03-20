package dao

import domain.Vignette
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class VignetteDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getAllVignettes() = em
        .createQuery("SELECT v FROM Vignette v", Vignette::class.java)
        .resultList

    fun addVignette(vignette: Vignette): Vignette {
        em.persist(vignette)
        return vignette
    }
}
