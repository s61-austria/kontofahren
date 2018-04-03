package singletons

import dao.InvoiceDao
import dao.UserDao
import dao.VehicleDao
import domain.Invoice
import domain.KontoUser
import domain.Profile
import domain.Vehicle
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import domain.enums.VehicleType
import java.util.Date
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Singleton
@Startup
class DummyData {

    /**
     * Unix times
     *
     * 1514825734000 -> 1 JAN 2018
     *
     * 1517500565000 -> 1 FEB 2018
     * 1519836934000 -> 28 FEB 2018
     *
     * 1522511765000 -> 31 MAR 2018
     * 1519919765000 -> 1 MAR 2018
     *
     * 1525103765000 -> 30 APR 2018
     */

    @Inject
    lateinit var invoiceDao: InvoiceDao

    @Inject
    lateinit var vehicleDao: VehicleDao

    @Inject
    lateinit var userDao: UserDao

    private val invoice1 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.OPEN, Date(1522511765000), Date(1517500565000), 0.0)
    private val invoice2 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.OPEN, Date(1522511765000), Date(1517500565000), 0.0)
    private val invoice3 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0)
    private val invoice4 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0)
    private val invoice5 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0)
    private val invoice6 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0)
    private val invoice7 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.PAID, Date(1519836934000), Date(1514825734000), 0.0)
    private val invoice8 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.PAID, Date(1519836934000), Date(1514825734000), 0.0)
    private val invoice9 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.CLOSED, Date(1525103765000), Date(1519919765000), 0.0)
    private val invoice10 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.CLOSED, Date(1525103765000), Date(1519919765000), 0.0)

    private val user1 = KontoUser("username1", "password1")
    private val user2 = KontoUser("username2", "password2")

    private val profile1 = Profile(user1)
    private val profile2 = Profile(user2)

    private val vehicle1 = Vehicle("27383937", "AB-3B-D2", VehicleType.PKW, profile1)
    private val vehicle2 = Vehicle("27343937", "A3-CD-6L", VehicleType.LKW, profile2)

    @PostConstruct
    fun setup() {

        vehicle1.owner = profile1
        vehicle2.owner = profile2

        invoice1.vehicle = vehicle1
        invoice1.profile = profile1
        invoice7.vehicle = vehicle1
        invoice7.profile = profile1
        invoice2.vehicle = vehicle2
        invoice2.profile = profile2
        invoice8.vehicle = vehicle2
        invoice8.profile = profile2

        userDao.persistUser(user1)
        userDao.persistUser(user2)

        vehicleDao.persistVehicle(vehicle1)
        vehicleDao.persistVehicle(vehicle2)

        invoiceDao.addInvoice(invoice1)
        invoiceDao.addInvoice(invoice2)
        invoiceDao.addInvoice(invoice3)
        invoiceDao.addInvoice(invoice4)
        invoiceDao.addInvoice(invoice5)
        invoiceDao.addInvoice(invoice6)
        invoiceDao.addInvoice(invoice7)
        invoiceDao.addInvoice(invoice8)
        invoiceDao.addInvoice(invoice9)
        invoiceDao.addInvoice(invoice10)
    }
}
