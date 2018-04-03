package service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.RateDao
import domain.Rate
import domain.enums.VehicleType
import domain.enums.VignetteType
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertSame

class RateServiceTest {
    lateinit var rateService: RateService

    var rate1 = Rate(VehicleType.LKW, kmPrice = 10.0, vignetteType = VignetteType.ONE_YEAR).apply {
        uuid = "randomId"
    }

    @Before
    fun setUp() {
        var rateDao = mock<RateDao> {
            on { addRate(any()) } doReturn rate1
            on { getRateByUuid(rate1.uuid) } doReturn rate1
        }.apply {
            Mockito.`when`(this.updateRate(com.nhaarman.mockito_kotlin.any()))
                .then({ i -> i.arguments[0] })
        }

        rateService = RateService(rateDao)
    }

    @Test
    fun testAddRate() {
        var result = rateService.addRate(rate1.vehicleType, rate1.kmPrice, rate1.vignetteType)

        assertSame(rate1, result)
    }

    @Test
    fun testUpdateRate() {
        var result = rateService.updateRate(rate1.uuid, VehicleType.MOTOR, 15.0, VignetteType.TEN_DAYS)

        assertEquals(VehicleType.MOTOR, result.vehicleType)
        assertEquals(15.0, result.kmPrice)
        assertEquals(VignetteType.TEN_DAYS, result.vignetteType)
    }
}
