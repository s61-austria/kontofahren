package singletons

import dao.CountryDao
import dao.InvoiceDao
import dao.RateDao
import dao.UserDao
import dao.VehicleDao
import domain.Country
import domain.Invoice
import domain.KontoUser
import domain.Profile
import domain.Rate
import domain.Vehicle
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import domain.enums.VehicleType
import domain.enums.VignetteType
import utils.sha256
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

    @Inject
    lateinit var countryDao: CountryDao

    @Inject
    lateinit var rateDao: RateDao

    private val country1 = Country("Austria")

    private val rate1 = Rate(VehicleType.LKW, VignetteType.ONE_YEAR, 0.22)
    private val rate2 = Rate(VehicleType.LKW, VignetteType.TEN_DAYS, 0.22)
    private val rate3 = Rate(VehicleType.LKW, VignetteType.TWO_MONTHS, 0.22)
    private val rate4 = Rate(VehicleType.PKW, VignetteType.ONE_YEAR, 0.18)
    private val rate5 = Rate(VehicleType.PKW, VignetteType.TEN_DAYS, 0.18)
    private val rate6 = Rate(VehicleType.PKW, VignetteType.TWO_MONTHS, 0.18)
    private val rate7 = Rate(VehicleType.MOTOR, VignetteType.ONE_YEAR, 0.16)
    private val rate8 = Rate(VehicleType.MOTOR, VignetteType.TEN_DAYS, 0.16)
    private val rate9 = Rate(VehicleType.MOTOR, VignetteType.TWO_MONTHS, 0.16)

    private val user1 = KontoUser("Jandie Hendriks", sha256("password1")).apply { profile = Profile(this) }
    private val user2 = KontoUser("Michel Mans", sha256("password2")).apply { profile = Profile(this) }

    private val vehicle1 = Vehicle("27383937", "AB-3B-D2", VehicleType.PKW, user1.profile).apply {
        rate = rate1
    }
    private val vehicle2 = Vehicle("27343937", "A3-CD-6L", VehicleType.LKW, user2.profile).apply {
        rate = rate2
    }

    private val invoice1 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.OPEN, Date(1522511765000), Date(1517500565000), 0.0).apply { country = country1; profile = user1.profile; vehicle = vehicle1 }
    private val invoice2 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.OPEN, Date(1522511765000), Date(1517500565000), 0.0).apply { country = country1; profile = user1.profile; vehicle = vehicle1 }
    private val invoice3 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0).apply { country = country1; profile = user1.profile; vehicle = vehicle1 }
    private val invoice4 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0).apply { country = country1; profile = user1.profile; vehicle = vehicle1 }
    private val invoice5 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0).apply { country = country1; profile = user1.profile; vehicle = vehicle1 }
    private val invoice6 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.OPEN, Date(1525103765000), Date(1519919765000), 0.0).apply { country = country1; profile = user2.profile; vehicle = vehicle2 }
    private val invoice7 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.PAID, Date(1519836934000), Date(1514825734000), 0.0).apply { country = country1; profile = user2.profile; vehicle = vehicle2 }
    private val invoice8 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.PAID, Date(1519836934000), Date(1514825734000), 0.0).apply { country = country1; profile = user2.profile; vehicle = vehicle2 }
    private val invoice9 = Invoice(InvoiceGenerationType.MANUAL, InvoiceState.CLOSED, Date(1525103765000), Date(1519919765000), 0.0).apply { country = country1; profile = user2.profile; vehicle = vehicle2 }
    private val invoice10 = Invoice(InvoiceGenerationType.AUTO, InvoiceState.CLOSED, Date(1525103765000), Date(1519919765000), 0.0).apply { country = country1; profile = user2.profile; vehicle = vehicle2 }

    @PostConstruct
    fun setup() {

        countryDao.persistCountry(country1)

        rateDao.addRate(rate1)
        rateDao.addRate(rate2)
        rateDao.addRate(rate3)
        rateDao.addRate(rate4)
        rateDao.addRate(rate5)
        rateDao.addRate(rate6)
        rateDao.addRate(rate7)
        rateDao.addRate(rate8)
        rateDao.addRate(rate9)

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
