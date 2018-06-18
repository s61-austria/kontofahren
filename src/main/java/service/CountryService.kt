package service

import dao.CountryDao

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class CountryService @Inject constructor(val countryDao: CountryDao) {
    fun getCountryByUUID(countryId: String) = countryDao.getCountryByUUID(countryId)
}
