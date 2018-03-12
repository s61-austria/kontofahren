package service

import dao.InvoiceDao
import domain.Invoice
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import java.util.*

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class InvoiceService @Inject constructor(
        val invoiceDao: InvoiceDao
){

    fun allInvoices(): List<Invoice> = invoiceDao.allInvoices();

    fun getInvoiceById(id: String): Invoice = invoiceDao.getInvoiceById(id)

    fun allInvoicesByVehicle(id: String): List<Invoice> = invoiceDao.allInvoicesByVehicle(id);

    fun allInvoicesByCivilian(id: String): List<Invoice> = invoiceDao.allInvoicesByProfile(id);

    fun allInvoicesCreatedBetweenDates(start: String, end: String): List<Invoice> = invoiceDao.allInvoicesCreatedBetweenDates(Date(start.toLong()), Date(end.toLong()))

    fun allInvoicesForBetweenDates(start: String, end: String): List<Invoice> = invoiceDao.allInvoicesForBetweenDates(Date(start.toLong()), Date(end.toLong()))

    fun allInvoicesGeneratedBy(type: InvoiceGenerationType): List<Invoice> = invoiceDao.allInvoicesGeneratedBy(type)

    fun allInvoicesByState(state: InvoiceState): List<Invoice> = invoiceDao.allInvoicesByStatus(state)
}
