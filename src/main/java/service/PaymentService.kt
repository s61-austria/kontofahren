package service

import dao.InvoiceDao
import domain.Invoice
import domain.enums.InvoiceState
import utils.MollieHelper
import javax.inject.Inject

class PaymentService@Inject constructor(
    val invoiceDao: InvoiceDao,
    val mollieHelper: MollieHelper
) {

    fun createPayment(uuid: String): Invoice? {
        val invoice = invoiceDao.getInvoiceByUuid(uuid)

        if (invoice.totalPrice == 0.0)
            invoice.totalPrice = 5.0

        val payment = mollieHelper.createMolliePayment(invoice.uuid, invoice.totalPrice,
            invoice.createdFor)

        if (payment != null) {
            invoice.paymentId = payment.id
            invoice.payLink = payment.links.paymentUrl
            invoice.state = InvoiceState.PAID

            invoiceDao.updateInvoice(invoice)

            return invoice
        }

        return null
    }
}
