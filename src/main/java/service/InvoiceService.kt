package service

import dao.InvoiceDao
import dao.UserDao
import domain.Invoice
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import java.util.Date
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class InvoiceService @Inject constructor(
    val invoiceDao: InvoiceDao,
    val userDao: UserDao
) {

    fun allInvoices(): List<Invoice> = invoiceDao.allInvoices()

    fun getInvoiceByUuid(uuid: String): Invoice = invoiceDao.getInvoiceByUuid(uuid)

    fun allInvoicesByVehicle(id: String): List<Invoice> = invoiceDao.allInvoicesByVehicle(id)

    fun allInvoicesByCivilian(id: String): List<Invoice> {
        val profile = userDao.getUserByUuid(id)?.profile ?: return emptyList()
        return profile.invoices
    }

    /**
     * @param start Start filter time in millis
     * @param end End filter time in millis
     */
    fun allInvoicesCreatedBetweenDates(start: Long, end: Long): List<Invoice> = invoiceDao.allInvoicesCreatedBetweenDates(Date(start), Date(end))

    fun allInvoicesForBetweenDates(start: String, end: String): List<Invoice> = invoiceDao.allInvoicesForBetweenDates(Date(start.toLong()), Date(end.toLong()))

    fun allInvoicesGeneratedBy(type: InvoiceGenerationType): List<Invoice> = invoiceDao.allInvoicesGeneratedBy(type)

    fun allInvoicesByState(state: InvoiceState): List<Invoice> = invoiceDao.allInvoicesByStatus(state)

    fun updateInvoiceState(invoiceId: String, state: InvoiceState): Invoice {
        val invoice = invoiceDao.getInvoiceByUuid(invoiceId)
        invoice.state = state

        return invoiceDao.updateInvoice(invoice)
    }
}
