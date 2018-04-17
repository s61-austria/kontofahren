package service

import dao.InvoiceDao
import domain.Invoice
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import nl.stil4m.mollie.domain.Links
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import utils.MollieHelper
import utils.now
import nl.stil4m.mollie.domain.Payment
import java.util.Date
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PaymentServiceTest {

    @Mock
    lateinit var invoiceDao: InvoiceDao

    @Mock
    lateinit var mollieHelper: MollieHelper

    @InjectMocks
    lateinit var paymentService: PaymentService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        paymentService = PaymentService(invoiceDao, mollieHelper)
    }

    @Test
    fun testCreatePayment() {
        val invoice = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, now(), now(), 0.0).apply { totalPrice = 99.0 }
        val link = Links("testPaymentUrl/adsbakjd", "", "", Optional.empty(), Optional.empty())
        val payment = Payment("", "konto-test-id", "", "", "", "", Date(), "", "", "", "", 0.0, 0.0, 0.0, "", "", mutableMapOf(), link, mutableMapOf(), "", "", "", Optional.empty(), Optional.empty())

        Mockito.`when`(invoiceDao.getInvoiceByUuid(invoice.uuid))
            .thenReturn(invoice)

        Mockito.`when`(mollieHelper.createMolliePayment(invoice.uuid, invoice.totalPrice,
            invoice.createdFor))
            .thenReturn(payment)

        val result: Invoice = paymentService.createPayment(invoice.uuid)!!

        assertEquals(99.0, invoice.totalPrice)
        assertEquals(link.paymentUrl, invoice.payLink)
        assertEquals(InvoiceState.PAID, invoice.state)
    }

    @Test
    fun testCreatePaymentNoPayment() {
        val invoice = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, now(), now(), 0.0).apply { totalPrice = 99.0 }

        Mockito.`when`(invoiceDao.getInvoiceByUuid(invoice.uuid))
            .thenReturn(invoice)

        Mockito.`when`(mollieHelper.createMolliePayment(invoice.uuid, invoice.totalPrice,
            invoice.createdFor))
            .thenReturn(null)

        val result: Invoice? = paymentService.createPayment(invoice.uuid)

        assertNull(result)
    }
}
