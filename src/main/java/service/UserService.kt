package service

import dao.UserDao
import domain.KontoUser
import domain.Profile
import utils.sha256
import javax.inject.Inject

class UserService @Inject constructor(
    val userDao: UserDao
) {
    fun allUsers(): List<KontoUser> = userDao.allKontoUsers

    fun getUserByUsername(username: String): KontoUser? = userDao.getUserByUsername(username)

    fun registerUser(username: String, password: String): KontoUser? = userDao.persistUser(KontoUser(username, sha256(password)).apply { profile = Profile(this) } )
}
