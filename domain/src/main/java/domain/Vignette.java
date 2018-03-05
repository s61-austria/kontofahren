package domain;

import domain.enums.VehicleType;
import domain.enums.VignetteType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Michel on 27-2-2018.
 */
@Entity
public class Vignette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    private VehicleType vehicleType;
    @Enumerated
    private VignetteType vignetteType;
    @Column
    private double price;

    public Vignette(){}

    public Vignette(VehicleType vehicleType, VignetteType vignetteType, double price){
        this.vehicleType = vehicleType;
        this.vignetteType = vignetteType;
        this.price = price;
    }

    public void setId(Long id){ this.id = id; }

    public Long getId() { return this.id; }

    public void setVehicleType(VehicleType vehicleType){
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType(){
        return this.vehicleType;
    }

    public void setVignetteType(VignetteType vignetteType){
        this.vignetteType = vignetteType;
    }

    public VignetteType getVignetteType(){
        return this.vignetteType;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

}
