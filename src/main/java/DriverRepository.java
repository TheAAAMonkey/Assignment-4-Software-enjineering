import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

private ArrayList<Driver> drivers;

public DriverRepository() {
    drivers = new ArrayList<>();
}

public boolean addDriver(Driver driver) {

    if (driver == null) {
        return false;
    }

    if (!driver.getSatisfaction()) {
        return false;
    }

    for (Driver d : drivers) {
        if (d.getID().equals(driver.getID())) {
            return false;
        }
    }

    drivers.add(driver);
    return true;
}

public Driver retrieveDriver(String driverID) {

    for (Driver driver : drivers) {
        if (driver.getID().equals(driverID)) {
            return driver;
        }
    }

    return null;
}

public List<Driver> retrieveAllDrivers() {
    return drivers;
}

public boolean updateDriver(String driverID, String newAddress) {

    Driver driver = retrieveDriver(driverID);

    if (driver == null) {
        return false;
    }


    return true;
}

public int countDrivers() {
    return drivers.size();
}

}
