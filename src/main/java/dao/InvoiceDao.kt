package dao

import domain.Invoice
import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import java.util.*
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class InvoiceDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getInvoiceById(id: String): Invoice = em.find(Invoice::class.java, id)

    fun allInvoices(): List<Invoice> = em.createNamedQuery("Invoice.allInvoices").resultList as List<Invoice>

    fun allInvoicesByVehicle(vehicleId: String): List<Invoice> = em
            .createQuery("SELECT i FROM Invoice i JOIN i.vehicle v WHERE v.id = :id", Invoice::class.java)
            .setParameter("id", vehicleId)
            .resultList

    fun allInvoicesCreatedBetweenDates(start: Date, end: Date): List<Invoice> = em
            .createQuery("SELECT i FROM Invoice i WHERE i.createdOn BETWEEN :start AND :end", Invoice::class.java)
            .setParameter("start", start)
            .setParameter("end", end)
            .resultList

    fun allInvoicesForBetweenDates(start: Date, end: Date): List<Invoice> = em
            .createQuery("SELECT i FROM Invoice i WHERE i.generatedFor BETWEEN :start AND :end", Invoice::class.java)
            .setParameter("start", start)
            .setParameter("end", end)
            .resultList

    fun allInvoicesGeneratedBy(type: InvoiceGenerationType): List<Invoice> = em
            .createQuery("SELECT i FROM Invoice i WHERE i.generationType = :type", Invoice::class.java)
            .setParameter("type", type)
            .resultList

    fun allInvoicesByStatus(state: InvoiceState): List<Invoice> = em
            .createQuery("SELECT i FROM Invoice i WHERE i.state = :type", Invoice::class.java)
            .setParameter("state", state)
            .resultList

    fun getAllInvoicesByProfile(profileId: Long?): List<Invoice> {
        val query = em!!.createQuery("SELECT i FROM Invoice i " + "JOIN i.profile c WHERE c.id = :id", Invoice::class.java)

        return query.setParameter("id", profileId).resultList
    }

    fun addInvoice(invoice: Invoice): Invoice {
        em.persist(invoice)
        return invoice
    }

    fun updateInvoice(invoice: Invoice): Invoice {
        em.merge(invoice)
        return invoice
    }
}
