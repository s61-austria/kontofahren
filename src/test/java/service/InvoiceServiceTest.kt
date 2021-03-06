package service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.s61.integration.connector.InternationalConnector
import com.s61.integration.model.Countries.AUSTRIA
import dao.InvoiceDao
import dao.UserDao
import domain.Invoice
import domain.KontoUser
import domain.Profile
import domain.Vehicle
import domain.enums.InvoiceGenerationType.AUTO
import domain.enums.InvoiceGenerationType.MANUAL
import domain.enums.InvoiceState
import domain.enums.VehicleType
import org.joda.time.DateTime
import org.junit.Before
import org.mockito.Mockito
import singletons.EuropeanIntegration
import kotlin.collections.ArrayList
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class InvoiceServiceTest {
    lateinit var invoiceService: InvoiceService

    val user1 = KontoUser("Henk", "Maatwerk4Fun")
    val user3 = KontoUser("pieter", "SLAPSLAPSLAP_KLAPKLAPKLAP")
    val date1 = DateTime(2018, 1, 1, 0, 0).toDate()
    val date2 = DateTime(2019, 1, 1, 0, 0).toDate()

    val invoice1 = Invoice(generationType = AUTO)
    val invoice1b = Invoice(generationType = AUTO).apply {
        uuid = invoice1.uuid
        state = InvoiceState.PAID
    }

    val profile1 = Profile(user1)
    val profile2 = Profile(user3).apply {
        addInvoice(invoice1)
    }

    val user2 = KontoUser("Ingrid", "Maatwerk5Fun").apply {
        profile = profile2
    }

    val vehicle1 = Vehicle("103-13231432-238", "12-AB-390", VehicleType.PKW, profile1)

    val invoice2 = Invoice(generationType = MANUAL).apply {
        vehicle = vehicle1
    }

    val invoice3 = Invoice(generationType = AUTO).apply {
        state = InvoiceState.CLOSED
        vehicle = vehicle1
    }

    @Before
    fun setup() {
        val invoiceMock = mock<InvoiceDao>() {
            on { allInvoices() } doReturn ArrayList<Invoice>().apply {
                add(invoice1)
                add(invoice2)
                add(invoice3)
            }
            on { getInvoiceByUuid(invoice1.uuid) } doReturn invoice1
            on { allInvoicesGeneratedBy(AUTO) } doReturn ArrayList<Invoice>().apply {
                add(invoice1)
                add(invoice3)
            }
            on { allInvoicesByStatus(InvoiceState.CLOSED) } doReturn ArrayList<Invoice>().apply {
                add(invoice3)
            }
            on { allInvoicesByVehicle(vehicle1.uuid) } doReturn ArrayList<Invoice>().apply {
                add(invoice2)
                add(invoice3)
            }
            on { allInvoicesCreatedBetweenDates(date1, date2) } doReturn ArrayList<Invoice>().apply {
                add(invoice1)
                add(invoice2)
                add(invoice3)
            }
            on { updateInvoice(invoice1) } doReturn invoice1b
        }

        val userMock = mock<UserDao>() {
            on { getUserByUuid(user2.uuid) } doReturn user2
        }

        val vehicleServiceMock = mock<VehicleService> {}
        val connectorMock = mock<InternationalConnector>() {}

        Mockito.doNothing().`when`(connectorMock.publishStolenCar(any()))
        Mockito.doNothing().`when`(connectorMock.publishCar(any()))
        Mockito.doNothing().`when`(connectorMock.publishInvoice(any(), AUSTRIA))

        val europeanMock = mock<EuropeanIntegration>() {
            on { connection } doReturn connectorMock
        }

        invoiceService = InvoiceService(invoiceMock, userMock, vehicleServiceMock, europeanMock)
    }

    fun testAllInvoices() {
        var result = invoiceService.allInvoices()

        assertTrue(result.contains(invoice1))
        assertTrue(result.contains(invoice2))
        assertTrue(result.contains(invoice3))
    }

    fun testGetInvoiceById() {
        var result = invoiceService.getInvoiceByUuid(invoice1.uuid)

        assertEquals(invoice1.uuid, result?.uuid)
        assertSame(invoice1, result)
    }

    fun testAllInvoicesByVehicle() {
        var result = invoiceService.allInvoicesByVehicle(vehicle1.uuid)

        assertTrue(result.contains(invoice2))
        assertTrue(result.contains(invoice3))
    }

    fun testAllInvoicesByCivilian() {
        var result = invoiceService.allInvoicesByCivilian(user2.uuid)

        assertTrue(result.contains(invoice1))
    }

    fun testAllInvoicesCreatedBetweenDates() {
        var result = invoiceService.allInvoicesCreatedBetweenDates(date1.time, date2.time)

        assertTrue(result.contains(invoice1))
        assertTrue(result.contains(invoice2))
        assertTrue(result.contains(invoice3))
    }

    fun testAllInvoicesGeneratedBy() {
        var result = invoiceService.allInvoicesGeneratedBy(AUTO)

        assertTrue(result.contains(invoice1))
        assertTrue(result.contains(invoice3))
    }

    fun testAllInvoicesBystate() {
        var result = invoiceService.allInvoicesByState(InvoiceState.CLOSED)

        assertTrue(result.contains(invoice3))
    }

    fun testUpdateInvoiceState() {
        var result = invoiceService.updateInvoiceState(invoiceId = invoice1.uuid, state = InvoiceState.PAID)

        assertEquals(invoice1.uuid, result?.uuid)
    }
}
