package service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.UserDao
import dao.VehicleDao
import domain.KontoUser
import domain.Profile
import domain.Vehicle
import domain.enums.VehicleType
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VehicleServiceTest {
    lateinit var vehicleService: VehicleService

    val user1 = KontoUser("Henk", "Maatwerk4Fun", null)
    val profile1 = Profile(user1)

    val vehicle1 = Vehicle(
        "3759-8576854895959-212",
        vehicleType = VehicleType.LKW,
        licensePlate = "123-AB-12"
    )

    val vehicle2 = Vehicle(
        "2643-3753854543548-234",
        vehicleType = VehicleType.MOTOR,
        licensePlate = "CBJ-126"
    )

    @Before
    fun setUp() {
        val vehicleMock = mock<VehicleDao>() {
            on { allVehicles() } doReturn ArrayList<Vehicle>().apply {
                add(vehicle1)
                add(vehicle2)
            }
            on { getAllVehiclesInCountry("Austria") } doReturn ArrayList<Vehicle>().apply {
                add(vehicle1)
                add(vehicle2)
            }
            on { getVehicleByUuid(vehicle1.uuid) } doReturn vehicle1
            on { persistVehicle(vehicle1) } doReturn vehicle1
        }

        val userMock = mock<UserDao>() {
            on { getUserByUuid(user1.uuid) } doReturn user1
        }

        vehicleService = VehicleService(vehicleMock, userMock)
    }

    @Test
    fun getAllVehiclesInCountryTest() {
        val result = vehicleService.getAllVehiclesInCountry("Austria")

        Assert.assertEquals(2, result.size)
        Assert.assertTrue(result.contains(vehicle1))
        Assert.assertTrue(result.contains(vehicle2))
    }

    @Test
    fun getAllVehicles() {
        val result = vehicleService.allVehicles()

        Assert.assertEquals(2, result.size.toLong())
        Assert.assertTrue(result.contains(vehicle1))
        Assert.assertTrue(result.contains(vehicle2))
    }

    @Test
    fun testAddVehicles() {
        val result = vehicleService.addVehicle(vehicle1.hardwareSerialNumber,
            vehicle1.vehicleType, vehicle1.licensePlate)

        Assert.assertEquals(vehicle1.licensePlate, result.licensePlate)
        Assert.assertEquals(vehicle1.vehicleType, result.vehicleType)
        Assert.assertEquals(vehicle1.hardwareSerialNumber, result.hardwareSerialNumber)
    }

    @Test
    fun testSaveVehicle() {
        val result = vehicleService.saveVehicle(vehicle1.uuid, vehicle1.licensePlate, user1.uuid)

        Assert.assertEquals(vehicle1.licensePlate, result.licensePlate)
        Assert.assertEquals(vehicle1.hardwareSerialNumber, result.hardwareSerialNumber)
        Assert.assertEquals(vehicle1.vehicleType, result.vehicleType)
    }
}
