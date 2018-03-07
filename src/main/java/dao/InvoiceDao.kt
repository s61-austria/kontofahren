package dao

import domain.Invoice
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class InvoiceDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun allInvoices(): List<Invoice> = em.createQuery("SELECT i FROM Invoice i", Invoice::class.java).resultList

    fun getInvoiceById(id: Long): Invoice = em.find(Invoice::class.java, id)

    fun getAllInvoicesByVehicle(vehicleId: Long): List<Invoice> =
            em.createQuery("SELECT i FROM Invoice i JOIN i.vehicle v WHERE v.id = :id", Invoice::class.java)
                    .setParameter("id", vehicleId)
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
