package service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.InvoiceDao
import dao.UserDao
import domain.Activity
import domain.Country
import domain.Invoice
import domain.KontoUser
import domain.Location
import domain.Profile
import domain.Rate
import domain.Vehicle
import domain.enums.InvoiceGenerationType.AUTO
import domain.enums.InvoiceGenerationType.MANUAL
import domain.enums.InvoiceState
import domain.enums.VehicleType
import domain.enums.VignetteType
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import utils.now
import java.util.Date
import kotlin.collections.ArrayList

class InvoiceServiceTest {
    lateinit var invoiceService: InvoiceService

    val user1 = KontoUser("Henk", "Maatwerk4Fun", null)
    val date1 = Date(2018, 1, 1)
    val date2 = Date(2019, 1, 1)

    val invoice1 = Invoice(generationType = AUTO)
    val invoice1b = Invoice(generationType = AUTO).apply {
        uuid = invoice1.uuid
        state = InvoiceState.PAID
    }

    val profile1 = Profile(user1)
    val profile2 = Profile(null).apply {
        addInvoice(invoice1)
    }

    val user2 = KontoUser("Ingrid", "Maatwerk5Fun", profile2)

    val vehicle1 = Vehicle("103-13231432-238", "12-AB-390", VehicleType.PKW, profile1)

    val invoice2 = Invoice(generationType = MANUAL).apply {
        vehicle = vehicle1
    }

    val invoice3 = Invoice(generationType = AUTO).apply {
        state = InvoiceState.CLOSED
        vehicle = vehicle1
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        invoiceService = InvoiceService(invoiceDaoMock!!, userDaoMock!!)
    }

    @Test
    fun testAllInvoices() {
        var result = invoiceService.allInvoices()

        Assert.assertTrue(result.contains(invoice1))
        Assert.assertTrue(result.contains(invoice2))
        Assert.assertTrue(result.contains(invoice3))
    }

    @Test
    fun testGetInvoiceById() {
        var result = invoiceService.getInvoiceByUuid(invoice1.uuid)

        Assert.assertEquals(invoice1.uuid, result.uuid)
        Assert.assertSame(invoice1, result)
    }

    @Test
    fun testAllInvoicesByVehicle() {
        var result = invoiceService.allInvoicesByVehicle(vehicle1.uuid)

        Assert.assertTrue(result.contains(invoice2))
        Assert.assertTrue(result.contains(invoice3))
    }

    @Test
    fun testAllInvoicesByCivilian() {
        var result = invoiceService.allInvoicesByCivilian(user2.uuid)

        Assert.assertTrue(result.contains(invoice1))
    }

    @Test
    fun testAllInvoicesCreatedBetweenDates() {
        var result = invoiceService.allInvoicesCreatedBetweenDates(date1.time.toString(), date2.time.toString())

        Assert.assertTrue(result.contains(invoice1))
        Assert.assertTrue(result.contains(invoice2))
        Assert.assertTrue(result.contains(invoice3))
    }

    @Test
    fun testAllInvoicesGeneratedBy() {
        var result = invoiceService.allInvoicesGeneratedBy(AUTO)

        Assert.assertTrue(result.contains(invoice1))
        Assert.assertTrue(result.contains(invoice3))
    }

    @Test
    fun testAllInvoicesBystate() {
        var result = invoiceService.allInvoicesByState(InvoiceState.CLOSED)

        Assert.assertTrue(result.contains(invoice3))
    }

    @Test
    fun testUpdateInvoiceState() {
        var result = invoiceService.updateInvoiceState(invoiceId = invoice1.uuid, state = InvoiceState.PAID)

        Assert.assertEquals(invoice1.uuid, result.uuid)
        Assert.assertSame(invoice1b, result)
    }

    @Test
    fun testCalculateInvoices() {
        val profile1 = Profile(KontoUser("", "", null))
        val country = Country("Nederland")
        val location1 = Location(country,
            51.457065, 5.476294, now())
        val location2 = Location(country,
            51.456346, 5.477750, now())
        val location3 = Location(country,
            51.453946, 5.480196, now())
        val activity1 = Activity(country, profile1).apply {
            locations = arrayListOf(location1, location2)
        }
        val activity2 = Activity(country, profile1).apply {
            locations = arrayListOf(location1, location2, location3)
        }
        val rate = Rate(VehicleType.LKW, VignetteType.TEN_DAYS, 0.1)
        val vehicle1 = Vehicle("1234",
            "TF-09-PP", VehicleType.LKW, profile1, rate).apply {
            activities = arrayListOf(activity1)
        }
        val vehicle2 = Vehicle("12345",
            "TF-09-XYZ", VehicleType.LKW, profile1, rate).apply {
            activities = arrayListOf(activity2)
        }
        val vehicles = arrayListOf<Vehicle>(vehicle1, vehicle2)

        Mockito.`when`(vehicleServiceMock!!.allVehicles()).thenReturn(vehicles)

        val results = invoiceService.generateVehiclesInvoices(country, now())

        assertEquals(2, results.size)

        assertEquals(0.128, results[0].kilometers, 0.001)
        assertEquals(0.445, results[1].kilometers, 0.001)
    }
}
