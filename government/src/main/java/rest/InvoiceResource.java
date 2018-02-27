package rest;

import domain.Invoice;
import service.InvoiceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("invoices")
@Stateless
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @GET
    @Produces("application/json")
    public List<Invoice> getAllInvoices(){
        return invoiceService.getAllInvoices();
    }
}
