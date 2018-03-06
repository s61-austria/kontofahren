package service;

import dao.InvoiceDao;
import domain.Invoice;
import domain.enums.InvoiceGenerationType;
import domain.enums.InvoiceStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
public class InvoiceService {

    @Inject
    InvoiceDao invoiceDao;

    public Invoice getInvoiceById(Long id) {
        return invoiceDao.getInvoiceById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }

    public List<Invoice> getAllInvoicesByVehicle(Long id) {
        return invoiceDao.getAllInvoicesByVehicle(id);
    }

    public List<Invoice> getAllInvoicesByCivilian(Long id) {
        return invoiceDao.getAllInvoicesByCivilian(id);
    }

    public List<Invoice> getAllInvoicesCreatedBetweenDates(Date start, Date end) {
        return invoiceDao.getAllInvoicesCreatedBetweenDates(start, end);
    }

    public List<Invoice> getAllInvoicesForBetweenDates(Date start, Date end) {
        return invoiceDao.getAllInvoicesForBetweenDates(start, end);
    }

    public List<Invoice> getAllInvoicesGeneratedBy(InvoiceGenerationType type) {
        return invoiceDao.getAllInvoicesGeneratedBy(type);
    }

    public List<Invoice> getAllInvoicesByStatus(InvoiceStatus status) {
        return invoiceDao.getAllInvoicesByStatus(status);
    }
}
