package service

import dao.ProfileDao
import domain.Profile
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class ProfileService @Inject constructor(
    private val profileDao: ProfileDao
) {
    fun getAllProfiles(userUUID: String?): MutableList<Profile> {


        if (!userUUID.isNullOrBlank()) {
            val profile = profileDao.getProfileByUuid(userUUID!!)

            return if (profile == null) mutableListOf<Profile>() else mutableListOf(profile)
        }

        return profileDao.allProfiles()
    }
}
