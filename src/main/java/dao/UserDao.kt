package dao

import domain.User

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery

@Stateless
class UserDao {
    @PersistenceContext
    internal var em: EntityManager? = null

    val allUsers: List<User>
        get() {
            val query = em!!.createQuery("SELECT u FROM KontoUser u", User::class.java)

            return query.resultList
        }

    fun persistUser(user: User): User {
        em!!.persist(user)

        return user
    }

    fun getUserById(id: Long): User {
        val query = em!!.createQuery("SELECT u FROM User u WHERE u.id = :id", User::class.java)

        return query.singleResult
    }
}
