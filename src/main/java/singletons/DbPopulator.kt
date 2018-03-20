package singletons

import dao.UserDao
import domain.KontoGroup
import domain.KontoUser
import utils.Open
import utils.sha256
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.inject.Inject

@Open
@Singleton
class DbPopulator @Inject constructor(
    val userDao: UserDao
) {

    @PostConstruct
    fun populate() {
        val john = KontoUser(
            "john",
            sha256("john"),
            null
        )
        val regularGroup = KontoGroup(
            "regulars"
        )

        userDao.persistUser(john)
        userDao.persistGroup(regularGroup)

        userDao.addToGroup(john, regularGroup)
    }
}
