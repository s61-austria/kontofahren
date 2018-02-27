package domain;

import javax.persistence.*;
import java.io.Serializable;
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
    @ManyToOne
    private List<Activity> activities;
    @OneToMany
    private Civilian owner;
    @OneToMany
    private Location currentLocation;

    public Vehicle() {}

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
}
