package domain;

import domain.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hardwareSerialNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activities;
    @ManyToOne(cascade = CascadeType.ALL)
    private Civilian owner;
    @ManyToOne(cascade = CascadeType.ALL)
    private Location currentLocation;

    public Vehicle() {}

    public Vehicle(String hardwareSerialNumber, VehicleType vehicleType, Location currentLocation) {
        this.hardwareSerialNumber = hardwareSerialNumber;
        this.vehicleType = vehicleType;
        this.currentLocation = currentLocation;
        this.activities = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHardwareSerialNumber() {
        return hardwareSerialNumber;
    }

    public void setHardwareSerialNumber(String hardwareSerialNumber) {
        this.hardwareSerialNumber = hardwareSerialNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Civilian getOwner() {
        return owner;
    }

    public void setOwner(Civilian owner) {
        this.owner = owner;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
