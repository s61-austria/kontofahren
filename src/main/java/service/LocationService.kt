package service

import dao.LocationDao
import domain.Location
import utils.Open
import javax.ejb.Stateless
import javax.inject.Inject

@Open
@Stateless
class LocationService @Inject constructor(
    val locationDao: LocationDao
) {
    fun saveLocation(location: Location) = locationDao.createLocation(location)
}
