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

    //public List<Invoice> Get

/*    public List<Vehicle> GetAllVehicles() {
        TypedQuery<Vehicle> query
                = em.createQuery("SELECT v FROM Vehicle v", Vehicle.class);

        return query.getResultList();
    }

    public List<Vehicle> GetAllVehiclesInCountry(String countryName) {
        TypedQuery<Vehicle> query
                = em.createQuery("SELECT v FROM Vehicle v " +
                "WHERE v.currentLocation.country.name = :countryName", Vehicle.class);

        return query.setParameter("countryName", countryName).getResultList();
    }*/
}
