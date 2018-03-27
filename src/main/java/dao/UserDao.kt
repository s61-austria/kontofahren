package dao

import domain.KontoUser

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class UserDao {
    @PersistenceContext
    internal var em: EntityManager? = null

    val allKontoUsers: List<KontoUser>
        get() {
            val query = em!!.createQuery("SELECT u FROM KontoUser u", KontoUser::class.java)

            return query.resultList
        }

    fun persistUser(kontoUser: KontoUser): KontoUser {
        em!!.persist(kontoUser)

        return kontoUser
    }

    fun getUserById(id: Long): KontoUser {
        val query = em!!.createQuery("SELECT u FROM KontoUser u WHERE u.id = :id", KontoUser::class.java)

        return query.singleResult
    }
}
