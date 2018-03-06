package dao

import domain.Invoice
import domain.Vehicle

import javax.ejb.Stateless
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery

@Stateless
class InvoiceDao {

    @PersistenceContext
    internal var em: EntityManager? = null

    val allInvoices: List<Invoice>
        get() {
            val query = em!!.createQuery("SELECT i FROM Invoice i", Invoice::class.java)

            return query.resultList
        }

    fun getInvoiceById(id: Long?): Invoice {
        return em!!.find(Invoice::class.java, id)
    }

    fun getAllInvoicesByVehicle(vehicleId: Long?): List<Invoice> {
        val query = em!!.createQuery("SELECT i FROM Invoice i " + "JOIN i.vehicle v WHERE v.id = :id", Invoice::class.java)

        return query.setParameter("id", vehicleId).resultList
    }

    fun getAllInvoicesByProfile(profileId: Long?): List<Invoice> {
        val query = em!!.createQuery("SELECT i FROM Invoice i " + "JOIN i.profile c WHERE c.id = :id", Invoice::class.java)

        return query.setParameter("id", profileId).resultList
    }

    fun addInvoice(invoice: Invoice): Invoice {
        em!!.persist(invoice)

        return invoice
    }

    fun updateInvoice(invoice: Invoice): Invoice {
        em!!.merge(invoice)

        return invoice
    }
}
