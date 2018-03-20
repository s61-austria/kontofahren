package service

import dao.UserDao
import dao.VehicleDao
import domain.Vehicle
import domain.enums.VehicleType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.* // ktlint-disable no-wildcard-imports

class VehicleServiceTest {
    val vehicle1 = Vehicle(
        "dwadawdaw",
        vehicleType = VehicleType.LKW,
        licensePlate = "haha ja"
    )

    val vehicle2 = Vehicle(
        "dawwa",
        vehicleType = VehicleType.MOTOR,
        licensePlate = "haha nee"
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

    @Test
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

    @Test
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
