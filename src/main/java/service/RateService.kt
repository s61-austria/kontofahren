package service

import dao.RateDao
import domain.Rate
import domain.enums.VehicleType
import domain.enums.VignetteType

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class RateService @Inject constructor(val rateDao: RateDao) {

    fun getAllRates() = rateDao.getAllRates() ?: emptyList<Rate>()

    fun getRateByUuid(rateId: String) = rateDao.getRateByUuid(rateId)

    fun addRate(vehicleType: VehicleType, kmPrice: Double, vignetteType: VignetteType): Rate {
        val r = Rate(vehicleType, kmPrice = kmPrice, vignetteType = vignetteType)

        return rateDao.addRate(r)
    }

    fun updateRate(rateId: String, vehicleType: VehicleType, kmPrice: Double, vignetteType: VignetteType): Rate {
        val r = rateDao.getRateByUuid(rateId)
        r.vehicleType = vehicleType
        r.kmPrice = kmPrice
        r.vignetteType = vignetteType

        return rateDao.updateRate(r)
    }
}
