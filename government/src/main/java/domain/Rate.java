package domain;

import domain.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    private VehicleType vt;
    @Column
    private double kmPrice;
    @Column
    private double startPrice;

    public Rate(){ }

    public Rate(VehicleType vt, double kmPrice, double startPrice){
        this.vt = vt;
        this.kmPrice = kmPrice;
        this.startPrice = startPrice;
    }

    public VehicleType getVt() {
        return vt;
    }

    public void setVt(VehicleType vt) {
        this.vt = vt;
    }

    public double getKmPrice() {
        return kmPrice;
    }

    public void setKmPrice(double kmPrice) {
        this.kmPrice = kmPrice;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }
}
