package domain;

import exceptions.KontoException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@DiscriminatorValue("Civilian")
public class Civilian extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private List<Vehicle> vehicles;
    @OneToMany
    private List<Invoice> invoices;

    public Civilian() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) throws KontoException{
        if(!vehicles.contains(vehicle)){
            vehicles.add(vehicle);
        }
        throw new KontoException("user already owns car");
    }

    public void removeVehicle(Vehicle vehicle) throws KontoException {
        if(vehicles.contains(vehicle)){
            vehicles.remove(vehicle);
        }
        else{
            throw new KontoException("user does not own this car "+vehicle.getHardwareSerialNumber());
        }
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) throws KontoException {
        if(!invoices.contains(invoice)) {
            invoices.add(invoice);
        }else{
            throw new KontoException("user already has invoice");
        }
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
