package dao;

import domain.Invoice;
import domain.Vehicle;
import domain.enums.InvoiceGenerationType;
import domain.enums.InvoiceStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Stateless
public class InvoiceDao {

    @PersistenceContext
    EntityManager em;

    public Invoice getInvoiceById(Long id){
        return em.find(Invoice.class, id);
    }

    public List<Invoice> getAllInvoices(){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i", Invoice.class);

        return query.getResultList();
    }

    public List<Invoice> getAllInvoicesByVehicle(Long vehicleId){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i " +
                "JOIN i.vehicle v WHERE v.id = :id", Invoice.class);

        return query.setParameter("id", vehicleId).getResultList();
    }

    public List<Invoice> getAllInvoicesByCivilian(Long civilianId){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i " +
                "JOIN i.civilian c WHERE c.id = :id", Invoice.class);

        return query.setParameter("id", civilianId).getResultList();
    }

    public List<Invoice> getAllInvoicesCreatedBetweenDates(Date start, Date end){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i " +
                "WHERE i.createdOn BETWEEN :start AND :end", Invoice.class);

        return query.setParameter("start", start).setParameter("end", end).getResultList();
    }

    public List<Invoice> getAllInvoicesForBetweenDates(Date start, Date end){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i " +
                "WHERE i.generatedFor BETWEEN :start AND :end", Invoice.class);

        return query.setParameter("start", start).setParameter("end", end).getResultList();
    }

    public List<Invoice> getAllInvoicesGeneratedBy(InvoiceGenerationType type){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i " +
                "WHERE i.generationType = :type", Invoice.class);

        return query.setParameter("type", type).getResultList();
    }

    public List<Invoice> getAllInvoicesByStatus(InvoiceStatus status){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i " +
                "WHERE i.status = :status", Invoice.class);

        return query.setParameter("status", status).getResultList();
    }

    public Invoice addInvoice(Invoice invoice){
        em.persist(invoice);

        return invoice;
    }

    public Invoice updateInvoice(Invoice invoice){
        em.merge(invoice);

        return invoice;
    }
}
