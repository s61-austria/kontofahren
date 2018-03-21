package service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test
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

        val vehicleServiceMock = mock<VehicleService>()

        invoiceService = InvoiceService(invoiceMock, userMock, vehicleServiceMock)
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
}
