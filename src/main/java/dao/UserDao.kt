package dao

import domain.KontoGroup
import domain.KontoUser

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class UserDao {
    @PersistenceContext
    lateinit var em: EntityManager

    val allKontoUsers: List<KontoUser> = em.createQuery("SELECT u FROM KontoUser u", KontoUser::class.java).resultList

    fun persistUser(kontoUser: KontoUser) = em.persist(kontoUser)

    fun persistGroup(kontoGroup: KontoGroup) = em.persist(kontoGroup)

    fun addToGroup(kontoUser: KontoUser, kontoGroup: KontoGroup): Boolean {
        val success = kontoGroup.users.add(kontoUser)
        kontoUser.groups.add(kontoGroup)
        em.merge(kontoGroup)

        return success
    }

    fun getUserById(id: String): KontoUser {
        val query = em.createQuery("SELECT u FROM KontoUser u WHERE u.id = :id", KontoUser::class.java)

        return query.singleResult
    }
}
