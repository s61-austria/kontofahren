package service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.InvoiceDao
import dao.UserDao
import domain.Invoice
import domain.KontoUser
import domain.Profile
import domain.enums.InvoiceGenerationType.AUTO
import domain.enums.InvoiceGenerationType.MANUAL
import domain.enums.InvoiceState
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import utils.now
import java.sql.Timestamp
import java.util.*

class InvoiceServiceTest {
    lateinit var invoiceService: InvoiceService

    val invoice1 = Invoice(generationType = AUTO)

    val invoice2 = Invoice(generationType = MANUAL)

    val invoice3 = Invoice(generationType = AUTO).apply {
        state = InvoiceState.CLOSED
    }

    //val user1 = KontoUser("Henk", "Maatwerk4Fun", Profile(user1))

    @Before
    fun setup() {
        val invoiceMock = mock<InvoiceDao>() {
            on { allInvoices() } doReturn ArrayList<Invoice>().apply {
                add(invoice1)
                add(invoice2)
                add(invoice3)
            }
            on { getInvoiceById(invoice1.id) } doReturn invoice1
            on { allInvoicesGeneratedBy(AUTO) } doReturn ArrayList<Invoice>().apply {
                add(invoice1)
                add(invoice3)
            }
            on { allInvoicesByStatus(InvoiceState.CLOSED) } doReturn ArrayList<Invoice>().apply {
                add(invoice3)
            }
        }

        val userMock = mock<UserDao>() {
            on {  }
        }

        invoiceService = InvoiceService(invoiceMock, userMock);
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

        Assert.assertEquals(3, result.size.toLong())
        Assert.assertTrue(result.contains(invoice1))
        Assert.assertTrue(result.contains(invoice2))
        Assert.assertTrue(result.contains(invoice3))
    }

    @Test
    fun testGetInvoiceById() {
        val invoice = invoice1
        invoice.totalPrice = 100.00

        Mockito.`when`(invoiceDaoMock!!.getInvoiceById("testid"))
                .thenReturn(invoice)

        val result = invoiceService.getInvoiceById("testid")

        Assert.assertEquals(invoice.id, result.id)
        Assert.assertEquals(invoice.createdOn, result.createdOn)
        Assert.assertEquals(invoice.generatedFor, result.generatedFor)
        Assert.assertEquals(invoice.generationType, result.generationType)
    }

    @Test
    fun testGetInvoicesByVehicle() {
        val invoices = ArrayList<Invoice>()

        invoices.add(invoice1)
        invoices.add(invoice2)

        Mockito.`when`(invoiceDaoMock!!.allInvoicesByVehicle("vehicleId"))
                .thenReturn(invoices)

        val result = invoiceService.allInvoicesByVehicle("vehicleId")

        Assert.assertEquals(2, result.size.toLong())
        Assert.assertTrue(result.contains(invoice1))
        Assert.assertTrue(result.contains(invoice2))
    }
}
