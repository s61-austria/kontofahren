package dao

import domain.Invoice
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import logger
import java.util.Date

import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class InvoiceDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getInvoiceByUuid(uuid: String): Invoice? = try {
        em
            .createQuery("SELECT i FROM Invoice i WHERE i.uuid = :uuid", Invoice::class.java)
            .setParameter("uuid", uuid)
            .singleResult
    } catch (e: Exception) {
        logger.warn("Failed to retrieve invoice for id $uuid")
        null
    }

    fun allInvoices(): List<Invoice> = em.createNamedQuery("Invoice.allInvoices", Invoice::class.java).resultList

    fun allInvoicesByVehicle(vehicleId: String): List<Invoice> = em
        .createQuery("SELECT i FROM Invoice i JOIN i.vehicle v WHERE v.uuid = :uuid", Invoice::class.java)
        .setParameter("uuid", vehicleId)
        .resultList

    fun allInvoicesCreatedBetweenDates(start: Date, end: Date): List<Invoice> = em
        .createQuery("SELECT i FROM Invoice i WHERE i.createdOn BETWEEN :start AND :end", Invoice::class.java)
        .setParameter("start", start)
        .setParameter("end", end)
        .resultList

    fun allInvoicesForBetweenDates(start: Date, end: Date): List<Invoice> = em
        .createQuery("SELECT i FROM Invoice i WHERE i.expires BETWEEN :start AND :end", Invoice::class.java)
        .setParameter("start", start)
        .setParameter("end", end)
        .resultList

    fun allInvoicesGeneratedBy(type: InvoiceGenerationType): List<Invoice> = em
        .createQuery("SELECT i FROM Invoice i WHERE i.generationType = :type", Invoice::class.java)
        .setParameter("type", type)
        .resultList

    fun getAllInvoicesByProfile(profileId: Long?): List<Invoice> {
        val query = em.createQuery("SELECT i FROM Invoice i " + "JOIN i.profile c WHERE c.id = :id", Invoice::class.java)

        return query.setParameter("id", profileId).resultList
    }

    fun allInvoicesByStatus(state: InvoiceState): List<Invoice> = em
        .createQuery("SELECT i FROM Invoice i WHERE i.state = :type", Invoice::class.java)
        .setParameter("state", state)
        .resultList

    fun addInvoice(invoice: Invoice): Invoice {
        em.persist(invoice)
        return invoice
    }

    fun updateInvoice(invoice: Invoice): Invoice {
        em.merge(invoice)
        return invoice
    }
}
