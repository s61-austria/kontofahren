package service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.ProfileDao
import dao.UserDao
import dao.VehicleDao
import domain.KontoUser
import domain.Profile
import domain.Vehicle
import domain.enums.VehicleType
import org.junit.Before
import org.junit.Test
import singletons.EuropeanIntegrationPublisher
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VehicleServiceTest {
    lateinit var vehicleService: VehicleService

    val user1 = KontoUser("Henk", "Maatwerk4Fun")
    val profile1 = Profile(user1)

    val user2 = KontoUser("Jan", "Maatwerk4Fun")
    val profile2 = Profile(user2)

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
            on { getAllVehiclesInCountry(any()) } doReturn ArrayList<Vehicle>().apply {
                add(vehicle1)
                add(vehicle2)
            }
            on { getVehicleByUuid(vehicle1.uuid) } doReturn vehicle1
            on { getVehicleByUuid(vehicle2.uuid) } doReturn vehicle2
            on { persistVehicle(vehicle1) } doReturn vehicle1
            on { persistVehicle(vehicle2) } doReturn vehicle2
        }

        val userMock = mock<UserDao>() {
            on { getUserByUuid(user1.uuid) } doReturn user1
        }

        val profileDaoMock = mock<ProfileDao>() {
            on { getProfileByUuid(profile1.uuid) } doReturn profile1
            on { getProfileByUuid(profile2.uuid) } doReturn profile2
        }

        val eu = mock<EuropeanIntegrationPublisher> {
            on { publishCar(any()) } doReturn true
        }

        vehicleService = VehicleService(vehicleMock, userMock, profileDaoMock, eu)
    }

    @Test
    fun getAllVehiclesInCountryTest() {
        val result = vehicleService.getAllVehiclesInCountry("Austria")

        assertEquals(2, result.size)
        assertTrue(result.contains(vehicle1))
        assertTrue(result.contains(vehicle2))
    }

    @Test
    fun getAllVehicles() {
        val result = vehicleService.allVehicles()

        assertEquals(2, result.size.toLong())
        assertTrue(result.contains(vehicle1))
        assertTrue(result.contains(vehicle2))
    }

    @Test
    fun testAddVehicles() {
        val result = vehicleService.addVehicle(vehicle1.hardwareSerialNumber,
            vehicle1.vehicleType, vehicle1.licensePlate)

        assertEquals(vehicle1.licensePlate, result.licensePlate)
        assertEquals(vehicle1.vehicleType, result.vehicleType)
        assertEquals(vehicle1.hardwareSerialNumber, result.hardwareSerialNumber)
    }

    @Test
    fun testSaveVehicle() {
        val result = vehicleService.saveVehicle(vehicle1.uuid, vehicle1.licensePlate, profile1.uuid)

        assertEquals(vehicle1.licensePlate, result!!.licensePlate)
        assertEquals(vehicle1.hardwareSerialNumber, result.hardwareSerialNumber)
        assertEquals(vehicle1.vehicleType, result.vehicleType)
        assertEquals(0, vehicle1.pastOwners.count())
        assertEquals(vehicle1.owner, profile1)
        assertEquals(profile1.vehicles[0], vehicle1)
    }

    @Test
    fun testSaveVehicleMultipleOwners() {
        var result = vehicleService.saveVehicle(vehicle1.uuid, vehicle1.licensePlate, profile1.uuid)

        result = vehicleService.saveVehicle(result!!.uuid, result.licensePlate, profile2.uuid)

        assertEquals(vehicle1.licensePlate, result!!.licensePlate)
        assertEquals(vehicle1.hardwareSerialNumber, result.hardwareSerialNumber)
        assertEquals(vehicle1.vehicleType, result.vehicleType)
        assertEquals(1, vehicle1.pastOwners.count())
        assertEquals(profile1, vehicle1.pastOwners[0])
        assertEquals(vehicle1.owner, profile2)
        assertEquals(profile2.vehicles[0], vehicle1)
        assertEquals(0, profile1.vehicles.count())
    }

    @Test
    fun testSaveVehicleMultipleOwners2() {
        val result1 = vehicleService.saveVehicle(vehicle1.uuid, vehicle1.licensePlate, profile1.uuid)
        val result2 = vehicleService.saveVehicle(vehicle2.uuid, vehicle2.licensePlate, profile1.uuid)

        assertEquals(result1!!.owner, profile1)
        assertEquals(result2!!.owner, profile1)
        assertEquals(2, profile1.vehicles.count())
        assertEquals(result1, profile1.vehicles[0])
        assertEquals(result2, profile1.vehicles[1])
    }
}
