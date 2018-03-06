package domain;

import domain.enums.InvoiceGenerationType;
import domain.enums.InvoiceStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date createdOn;

    @Column
    private Date generatedFor;

    @Enumerated(EnumType.STRING)
    private InvoiceGenerationType generationType;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column
    private double totalPrice;

    @ManyToOne
    private Civilian civilian;

    @ManyToOne
    private Vehicle vehicle;

    public Invoice(Date createdOn, Date generatedFor, InvoiceGenerationType generationType){
        this.createdOn = createdOn;
        this.generatedFor = generatedFor;
        this.generationType = generationType;
        this.status = InvoiceStatus.OPEN;
        this.totalPrice = 0.00;
        this.civilian = null;
        this.vehicle = null;
    }

    public Invoice(){ }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getGeneratedFor() {
        return generatedFor;
    }

    public void setGeneratedFor(Date generatedFor) {
        this.generatedFor = generatedFor;
    }

    public InvoiceGenerationType getGenerationType() {
        return generationType;
    }

    public void setGenerationType(InvoiceGenerationType generationType) {
        this.generationType = generationType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Civilian getCivilian() {
        return civilian;
    }

    public void setCivilian(Civilian civilian) {
        this.civilian = civilian;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
