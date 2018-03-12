package service;

import dao.InvoiceDao;
import dao.UserDao;
import domain.Invoice;
import domain.enums.InvoiceGenerationType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceServiceTest {

    @Mock
    InvoiceDao invoiceDaoMock;

    @Mock
    UserDao userDaoMock;

    @InjectMocks
    InvoiceService invoiceService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        invoiceService = new InvoiceService(invoiceDaoMock, userDaoMock);
    }

    @Test
    public void testGetAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        Invoice invoice3 = new Invoice();

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDaoMock.allInvoices())
                .thenReturn(invoices);

        List<Invoice> result = invoiceService.allInvoices();

        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains(invoice1));
        Assert.assertTrue(result.contains(invoice2));
        Assert.assertTrue(result.contains(invoice3));
    }

    @Test
    public void testGetInvoiceById() {
        Invoice invoice = new Invoice(new Date(), new Date(), InvoiceGenerationType.MANUAL);
        invoice.setId("testid");
        invoice.setTotalPrice(100.00);

        Mockito.when(invoiceDaoMock.getInvoiceById("testid"))
                .thenReturn(invoice);

        Invoice result = invoiceService.getInvoiceById("testid");

        Assert.assertEquals(invoice.getId(), result.getId());
        Assert.assertEquals(invoice.getCreatedOn(), result.getCreatedOn());
        Assert.assertEquals(invoice.getGeneratedFor(), result.getGeneratedFor());
        Assert.assertEquals(invoice.getGenerationType(), result.getGenerationType());
    }

    @Test
    public void testGetInvoicesByVehicle() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();

        invoices.add(invoice1);
        invoices.add(invoice2);

        Mockito.when(invoiceDaoMock.allInvoicesByVehicle("vehicleId"))
                .thenReturn(invoices);

        List<Invoice> result = invoiceService.allInvoicesByVehicle("vehicleId");

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(invoice1));
        Assert.assertTrue(result.contains(invoice2));
    }
}
