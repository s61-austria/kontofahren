package service

import dao.RateDao
import domain.Rate
import domain.Vehicle
import domain.Vignette
import domain.enums.VehicleType
import domain.enums.VignetteType

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class RateService @Inject constructor(val rateDao: RateDao) {

    fun getAllRates() = rateDao.getAllRates() ?: emptyList<Rate>()

    fun getRateById(rateId: String) = rateDao.getRateById(rateId)

    fun getRateByVehicle(serialNumber: String): Rate {
        return rateDao.getRateByVehicle(serialNumber)
    }

    fun addRate(vehicleType: VehicleType, kmPrice: Double, vignetteType: VignetteType): Rate {
        val r = Rate(vehicleType, kmPrice, vignetteType)
        return rateDao.addRate(r)
    }

    fun updateRate(rateId: String, vehicleType: VehicleType, kmPrice: Double, startPrice: Double): Rate {

        return Rate()
    }

}