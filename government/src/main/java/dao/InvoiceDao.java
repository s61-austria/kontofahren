package dao;

import domain.Invoice;
import domain.Vehicle;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
                = em.createQuery("SELECT i FROM Invoice i "
                 + "JOIN i.vehicle v WHERE v.id = :id", Invoice.class);

        return query.setParameter("id", vehicleId).getResultList();
    }

    public List<Invoice> getAllInvoicesByCivilian(Long civilianId){
        TypedQuery<Invoice> query
                = em.createQuery("SELECT i FROM Invoice i "
                + "JOIN i.civilian c WHERE c.id = :id", Invoice.class);

        return query.setParameter("id", civilianId).getResultList();
    }
}
