package service

import dao.ProfileDao
import dao.VehicleDao
import domain.Profile
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class ProfileService @Inject constructor(
    private val profileDao: ProfileDao,
    private val vehicleDao: VehicleDao
) {
    fun getAllProfiles(vehicleUUID: String?): MutableList<Profile> {

        if (!vehicleUUID.isNullOrBlank()) {
            val profileList = vehicleDao.getVehicleByUuid(vehicleUUID!!).pastOwners
            return if (profileList == null) mutableListOf<Profile>() else profileList
        }

        return profileDao.allProfiles()
    }

    fun getProfile(userUUID: String): Profile? {
        return profileDao.getProfileByUuid(userUUID)
    }
}
