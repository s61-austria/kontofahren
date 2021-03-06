package service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.s61.integration.connector.InternationalConnector
import com.s61.integration.model.Countries.AUSTRIA
import dao.InvoiceDao
import dao.UserDao
import domain.Activity
import domain.Country
import domain.Invoice
import domain.KontoUser
import domain.Location
import domain.Point
import domain.Profile
import domain.Rate
import domain.Vehicle
import domain.enums.InvoiceGenerationType.AUTO
import domain.enums.InvoiceGenerationType.MANUAL
import domain.enums.InvoiceState
import domain.enums.VehicleType
import domain.enums.VehicleType.LKW
import domain.enums.VignetteType
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import singletons.EuropeanIntegration
import utils.now
import java.util.ArrayList
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceService2Test {

    private val invoice1 = Invoice(MANUAL, InvoiceState.OPEN, now(), now(), 0.0)
    private val invoice2 = Invoice(MANUAL, InvoiceState.OPEN, now(), now(), 0.0)
    private val invoice3 = Invoice(AUTO, InvoiceState.OPEN, now(), now(), 0.0)

    @Mock
    private var invoiceDaoMock: InvoiceDao? = null

    @Mock
    private var userDaoMock: UserDao? = null

    @Mock
    private var vehicleServiceMock: VehicleService? = null

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    val connectorMock = mock<InternationalConnector> {}

    val europeanMock = mock<EuropeanIntegration>() {
        on { connection } doReturn connectorMock
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.doNothing().`when`(connectorMock.publishStolenCar(any()))
        Mockito.doNothing().`when`(connectorMock.publishCar(any()))
        Mockito.doNothing().`when`(connectorMock.publishInvoice(any(), AUSTRIA))
        invoiceService = InvoiceService(invoiceDaoMock!!, userDaoMock!!, vehicleServiceMock!!, europeanMock)
    }

    // todo @Test
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

    // todo @Test
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

    // todo @Test
    fun testCalculateInvoices() {
        val profile1 = Profile(KontoUser("", ""))
        val country = Country("Austria")
        val vehicle = Vehicle(
            "1",
            "haha-ja",
            LKW
        )
        val location1 = Location(vehicle,
            Point(51.457065, 5.476294), now())
        val location2 = Location(vehicle,
            Point(51.456346, 5.477750), now())
        val location3 = Location(vehicle,
            Point(51.453946, 5.480196), now())

        val rate = Rate(VehicleType.LKW, VignetteType.TEN_DAYS, 0.1)

        val vehicle1 = Vehicle("1234",
            "TF-09-PP", VehicleType.LKW, profile1).apply {
            activities = mutableListOf()
            this.rate = rate
        }.apply {
            locations.add(location1)
            locations.add(location2)
        }

        val vehicle2 = Vehicle("12345",
            "TF-09-XYZ", VehicleType.LKW, profile1).apply {
            activities = mutableListOf()
            this.rate = rate
        }.apply {
            locations.add(location1)
            locations.add(location2)
            locations.add(location3)
        }
        val vehicles = arrayListOf(vehicle1, vehicle2)

        Mockito.`when`(vehicleServiceMock!!.allVehicles()).thenReturn(vehicles)

        val results = invoiceService.generateVehiclesInvoices(country, now())

        assertEquals(2, results.size)

        assertEquals(128.8639919576991, results[0].meters)
        assertEquals(445.35395505130776, results[1].meters)
    }

    // todo @Test
    fun testDistance() {
        val points = listOf(
            Point(51.457065, 5.476294),
            Point(51.456346, 5.477750),
            Point(51.453946, 5.480196)
        )

        val distance = invoiceService.distance(points)

        assertEquals(445.35395505130776, distance)
    }

    // todo @Test
    fun testRegenerateInvoice() {
        val profile1 = Profile(KontoUser("", ""))
        val country = Country("Nederland")
        val vehicle = Vehicle(
            "1",
            "haha-ja",
            LKW
        )
        val location1 = Location(vehicle,
            Point(51.457065, 5.476294), now())
        val location2 = Location(vehicle,
            Point(51.456346, 5.477750), now())
        val activity1 = Activity(profile1, vehicle).apply {
            locations = mutableListOf(location1, location2)
        }
        val rate = Rate(VehicleType.LKW, VignetteType.TEN_DAYS, 0.1)
        val vehicle1 = Vehicle("1234",
            "TF-09-PP", VehicleType.LKW, profile1).apply {
            activities = mutableListOf(activity1)
            this.rate = rate
        }.apply {
            locations.add(location1)
            locations.add(location2)
        }
        val vehicles = mutableListOf(vehicle1)

        val location3 = Location(vehicle,
            Point(51.453946, 5.480196), now())

        Mockito.`when`(vehicleServiceMock!!.allVehicles()).thenReturn(vehicles)

        val result = invoiceService.generateVehiclesInvoices(country, now())[0]
        result.uuid = ""

        assertEquals(128.8639919576991, result.meters)

        vehicle1.locations.add(location3)

        Mockito.`when`(invoiceDaoMock!!.getInvoiceByUuid("test")).thenReturn(result)

        val result2 = invoiceService.regenerateInvoice("test")!!

        assertEquals(445.35395505130776, result2.meters)
    }
}
