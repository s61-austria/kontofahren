package service;

import dao.VehicleDao;
import domain.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class VehicleServiceTest {

    @Mock
    VehicleDao vehicleDaoMock;

    @InjectMocks
    VehicleService vehicleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vehicleService = new VehicleService(vehicleDaoMock);
    }

    @Test
    public void getAllVehiclesInCountryTest() {
        String country = "Nederland";
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        Mockito.when(vehicleDaoMock.getAllVehiclesInCountry(country))
                .thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehiclesInCountry(country);

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(vehicle1));
        Assert.assertTrue(result.contains(vehicle2));
    }

    @Test
    public void getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        Mockito.when(vehicleDaoMock.getAllVehicles())
                .thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehicles();

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(vehicle1));
        Assert.assertTrue(result.contains(vehicle2));
    }
}
