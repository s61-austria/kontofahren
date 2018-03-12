package service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.RateDao
import domain.Rate
import domain.enums.VehicleType
import domain.enums.VignetteType
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RateServiceTest {
    lateinit var rateService: RateService

    var rate1 = Rate(VehicleType.LKW, 10.0, VignetteType.ONE_YEAR).apply {
        id = "randomId"
    }

    @Before
    fun setUp() {
        var rateDao = mock<RateDao> {
            on {addRate(any()) } doReturn rate1
            on {getRateById(rate1.id!!)} doReturn rate1
        }.apply {
            Mockito.`when`(this.updateRate(com.nhaarman.mockito_kotlin.any()))
                    .then({ i -> i.arguments[0] })
        }

        rateService = RateService(rateDao);
    }

    @Test
    fun testAddRate() {
        var result = rateService.addRate(rate1.vehicleType!!, rate1.kmPrice, rate1.vignetteType!!)

        assertSame(rate1, result)
    }

    @Test
    fun testUpdateRate() {
        var result = rateService.updateRate(rate1.id!!, VehicleType.MOTOR, 15.0, VignetteType.TEN_DAYS)

        assertEquals(VehicleType.MOTOR, result.vehicleType)
        assertEquals(15.0, result.kmPrice)
        assertEquals(VignetteType.TEN_DAYS, result.vignetteType)
    }
}
