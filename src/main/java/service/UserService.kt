package service

import dao.UserDao
import domain.KontoUser
import javax.inject.Inject

class UserService @Inject constructor(
        val userDao: UserDao
){
    fun allUsers(): List<KontoUser> = userDao.allKontoUsers

}
