package service

import dao.UserDao
import dao.VehicleDao
import domain.Location
import domain.Vehicle
import domain.enums.VehicleType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import utils.Open
import java.util.*


//todo re-anble tests
class VehicleServiceTest {
    val vehicle1 = Vehicle(
        "dwadawdaw",
        VehicleType.LKW,
        Location(),
        "haha ja"
    )

    val vehicle2 = Vehicle(
        "dawwa",
        VehicleType.MOTOR,
        Location(),
        "haha nee"
        )
    @Mock
    internal var vehicleDaoMock: VehicleDao? = null

    @Mock
    internal var userDaoMock: UserDao? = null

    @InjectMocks
    lateinit var vehicleService: VehicleService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vehicleService = VehicleService(vehicleDaoMock!!, userDaoMock!!)
    }

    fun getAllVehiclesInCountryTest() {
        val country = "Nederland"
        val vehicles = ArrayList<Vehicle>()

        vehicles.add(vehicle1)
        vehicles.add(vehicle2)

        Mockito.`when`(vehicleDaoMock!!.getAllVehiclesInCountry(country))
            .thenReturn(vehicles)

        val result = vehicleService.getAllVehiclesInCountry(country)

        Assert.assertEquals(2, result.size.toLong())
        Assert.assertTrue(result.contains(vehicle1))
        Assert.assertTrue(result.contains(vehicle2))
    }

    fun getAllVehicles() {
        val vehicles = ArrayList<Vehicle>()

        vehicles.add(vehicle1)
        vehicles.add(vehicle2)

        Mockito.`when`(vehicleDaoMock!!.allVehicles())
            .thenReturn(vehicles)

        val result = vehicleService.allVehicles()

        Assert.assertEquals(2, result.size.toLong())
        Assert.assertTrue(result.contains(vehicle1))
        Assert.assertTrue(result.contains(vehicle2))
    }
}
