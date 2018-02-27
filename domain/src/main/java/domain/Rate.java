package domain;

import domain.enums.VehicleType;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Rate implements Serializable {

    private VehicleType vt;
    private double kmPrice;
    private double startPrice;

    public Rate(){}

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
