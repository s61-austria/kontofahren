package dao;

import domain.Vehicle;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class VehicleDao {

    @PersistenceContext
    EntityManager em;

    public List<Vehicle> getAllVehicles() {
        TypedQuery<Vehicle> query
                = em.createQuery("SELECT v FROM Vehicle v", Vehicle.class);

        return query.getResultList();
    }

    public List<Vehicle> getAllVehiclesInCountry(String countryName) {
        TypedQuery<Vehicle> query
                = em.createQuery("SELECT v FROM Vehicle v " +
                "JOIN v.currentLocation l JOIN l.country c WHERE c.name = :countryName", Vehicle.class);

        return query.setParameter("countryName", countryName).getResultList();
    }

    public Vehicle getVehicleById(long id){
        TypedQuery<Vehicle> query
                = em.createQuery("SELECT v FROM Vehicle v WHERE v.id = :id", Vehicle.class);
        return query.getSingleResult();
    }

    public Vehicle persistVehicle(Vehicle vehicle) {
        em.persist(vehicle);

        return vehicle;
    }
}
