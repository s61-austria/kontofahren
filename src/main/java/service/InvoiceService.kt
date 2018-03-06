package service

import dao.InvoiceDao
import domain.Invoice

import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class InvoiceService @Inject constructor(
        val invoiceDao: InvoiceDao
){

    fun allInvoices(): List<Invoice> = invoiceDao.allInvoices();

    fun getInvoiceById(id: Long?): Invoice? = invoiceDao.getInvoiceById(id)
}
