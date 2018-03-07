package service

import dao.VignetteDao
import domain.Vignette
import domain.enums.VehicleType
import domain.enums.VignetteType
import javax.ejb.Stateless
import javax.inject.Inject


@Stateless
class VignetteService @Inject constructor(val vignetteDao: VignetteDao){

    fun getAllVignettes() = vignetteDao.getAllVignettes() ?: emptyList<Vignette>();

    fun addVignette(vehicleType: VehicleType, vignetteType: VignetteType, price: Double) :Vignette{
        val vignette = Vignette(vehicleType, vignetteType, price)
        return vignetteDao.addVignette(vignette);
    }

}