package service;

import dao.InvoiceDao;
import domain.Invoice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class InvoiceService {

    @Inject
    InvoiceDao invoiceDao;

    public List<Invoice> getAllInvoices(){
        return  invoiceDao.getAllInvoices();
    }

    public Invoice getInvoiceById(Long id){
        return invoiceDao.getInvoiceById(id);
    }
}
