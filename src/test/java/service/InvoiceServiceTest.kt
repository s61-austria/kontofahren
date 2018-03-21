package service

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
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import utils.now

import java.util.ArrayList

class InvoiceServiceTest {

    private val invoice1 = Invoice(MANUAL, InvoiceState.OPEN, null, null, now(), 0.0)
    private val invoice2 = Invoice(MANUAL, InvoiceState.OPEN, null, null, now(), 0.0)
    private val invoice3 = Invoice(AUTO, InvoiceState.OPEN, null, null, now(), 0.0)

    @Mock
    private var invoiceDaoMock: InvoiceDao? = null

    @Mock
    private var userDaoMock: UserDao? = null

    @Mock
    private var vehicleServiceMock: VehicleService? = null

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        invoiceService = InvoiceService(invoiceDaoMock!!, userDaoMock!!, vehicleServiceMock!!)
    }

    @Test
    fun testGetAllInvoices() {
        val invoices = ArrayList<Invoice>()

        invoices.add(invoice1)
        invoices.add(invoice2)
        invoices.add(invoice3)

        Mockito.`when`(invoiceDaoMock!!.allInvoices())
            .thenReturn(invoices)

        val result = invoiceService.allInvoices()

        assertEquals(3, result.size.toLong())
        assertTrue(result.contains(invoice1))
        assertTrue(result.contains(invoice2))
        assertTrue(result.contains(invoice3))
    }

    @Test
    fun testGetInvoiceById() {
        val invoice = invoice1
        invoice.totalPrice = 100.00

        Mockito.`when`(invoiceDaoMock!!.getInvoiceById("testid"))
            .thenReturn(invoice)

        val result = invoiceService.getInvoiceById("testid")

        assertEquals(invoice.uuid, result.uuid)
        assertEquals(invoice.createdOn, result.createdOn)
        assertEquals(invoice.expires, result.expires)
        assertEquals(invoice.generationType, result.generationType)
    }

    @Test
    fun testGetInvoicesByVehicle() {
        val invoices = ArrayList<Invoice>()

        invoices.add(invoice1)
        invoices.add(invoice2)

        Mockito.`when`(invoiceDaoMock!!.allInvoicesByVehicle("vehicleId"))
            .thenReturn(invoices)

        val result = invoiceService.allInvoicesByVehicle("vehicleId")

        assertEquals(2, result.size.toLong())
        assertTrue(result.contains(invoice1))
        assertTrue(result.contains(invoice2))
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
