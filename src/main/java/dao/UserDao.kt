package dao

import domain.KontoUser

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class UserDao {

    @PersistenceContext
    lateinit var em: EntityManager

    val allKontoUsers: List<KontoUser>
        get() {
            val query = em.createQuery("SELECT u FROM KontoUser u", KontoUser::class.java)

            return query.resultList
        }

    fun persistUser(kontoUser: KontoUser): KontoUser {
        em.persist(kontoUser)

        return kontoUser
    }

    fun getUserByUuid(userId: String): KontoUser? = em
        .createQuery("SELECT k FROM KontoUser k WHERE k.uuid = :userId", KontoUser::class.java)
        .setParameter("userId", userId)
        .singleResult
}
