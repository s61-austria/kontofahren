package service

import dao.InvoiceDao
import dao.UserDao
import domain.Invoice
import domain.enums.InvoiceGenerationType.AUTO
import domain.enums.InvoiceGenerationType.MANUAL
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

    private val invoice1 = Invoice(MANUAL, null, null, now(), 0.0)
    private val invoice2 = Invoice(MANUAL, null, null, now(), 0.0)
    private val invoice3 = Invoice(AUTO, null, null, now(), 0.0)

    @Mock
    private var invoiceDaoMock: InvoiceDao? = null

    @Mock
    private var userDaoMock: UserDao? = null

    @Mock
    private var vehicleService: VehicleService? = null

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        invoiceService = InvoiceService(invoiceDaoMock!!, userDaoMock!!, vehicleService!!)
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

        Assert.assertEquals(invoice.uuid, result.uuid)
        Assert.assertEquals(invoice.createdOn, result.createdOn)
        Assert.assertEquals(invoice.expires, result.expires)
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
