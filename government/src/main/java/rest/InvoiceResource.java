package rest;

import domain.Invoice;
import domain.enums.InvoiceGenerationType;
import domain.enums.InvoiceStatus;
import service.InvoiceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Date;
import java.util.List;

@Path("invoices")
@Stateless
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Invoice getInvoiceById(@PathParam("id") Long id){

        return invoiceService.getInvoiceById(id);
    }

    @GET
    @Produces("application/json")
    public List<Invoice> getAllInvoices(){

        return invoiceService.getAllInvoices();
    }

    @GET
    @Path("/vehicle/{id}")
    @Produces("application/json")
    public List<Invoice> getAllInvoicesByVehicleId(@PathParam("id") Long id) {

        return invoiceService.getAllInvoicesByVehicle(id);
    }

    @GET
    @Path("/civilian/{id}")
    @Produces("application/json")
    public List<Invoice> getAllInvoicesByCivilianId(@PathParam("id") Long id) {

        return invoiceService.getAllInvoicesByCivilian(id);
    }

    @GET
    @Path("/date/created/{startdate}/{enddate}")
    @Produces("application/json")
    public List<Invoice> getAllInvoicesCreatedBetweenDates(@PathParam("startdate")Date startDate,
                                                           @PathParam("enddate")Date endDate){

        return invoiceService.getAllInvoicesCreatedBetweenDates(startDate, endDate);
    }

    @GET
    @Path("/date/for/{startdate}/{enddate}")
    @Produces("application/json")
    public List<Invoice> getAllInvoicesForBetweenDates(@PathParam("startdate")Date startDate,
                                                       @PathParam("enddate")Date endDate){

        return invoiceService.getAllInvoicesForBetweenDates(startDate, endDate);
    }

    @GET
    @Path("/generated/{type}")
    @Produces("application/json")
    public List<Invoice> getAllInvoicesGeneratedBy(@PathParam("type")InvoiceGenerationType type){

        return invoiceService.getAllInvoicesGeneratedBy(type);
    }

    @GET
    @Path("/status/{status}")
    @Produces("application/json")
    public List<Invoice> getAllInvoicesByStatus(@PathParam("status")InvoiceStatus status){

        return invoiceService.getAllInvoicesByStatus(status);
    }
}
