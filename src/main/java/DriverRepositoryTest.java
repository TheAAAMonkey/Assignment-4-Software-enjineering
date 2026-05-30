import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DriverRepositoryTest {

    @Test
    void validDriverStoredCorrectly() {

        DriverRepository repo = new DriverRepository();

        ArrayList<String> ids = new ArrayList<>();

        Driver driver = new Driver(
                "4445!T^8RR",
                "Apple",
                2,
                "Light",
                "12|Apple|Apple|aooke|apple",
                "12-11-1220",
                ids
        );

        assertTrue(repo.addDriver(driver));
        assertEquals(1, repo.countDrivers());
    }

    @Test
    void invalidDriverRejected() {

        DriverRepository repo = new DriverRepository();

        ArrayList<String> ids = new ArrayList<>();

        Driver driver = new Driver(
                "BADID",
                "Apple",
                2,
                "Light",
                "WrongAddress",
                "12/11/1220",
                ids
        );

        assertFalse(repo.addDriver(driver));
        assertEquals(0, repo.countDrivers());
    }

    @Test
    void retrieveDriverWorks() {

        DriverRepository repo = new DriverRepository();

        ArrayList<String> ids = new ArrayList<>();

        Driver driver = new Driver(
                "4445!T^8RR",
                "Apple",
                2,
                "Light",
                "12|Apple|Apple|aooke|apple",
                "12-11-1220",
                ids
        );

        repo.addDriver(driver);

        Driver found = repo.retrieveDriver("4445!T^8RR");

        assertNotNull(found);
        assertEquals("Apple", found.getName());
    }

    @Test
    void countDriversWorks() {

        DriverRepository repo = new DriverRepository();

        ArrayList<String> ids = new ArrayList<>();

        Driver driver1 = new Driver(
                "4445!T^8RR",
                "Apple",
                2,
                "Light",
                "12|Apple|Apple|aooke|apple",
                "12-11-1220",
                ids
        );

        Driver driver2 = new Driver(
                "5556@Y#9AA",
                "John",
                5,
                "Heavy",
                "10|King|Melbourne|VIC|Australia",
                "10-10-2000",
                ids
        );

        repo.addDriver(driver1);
        repo.addDriver(driver2);

        assertEquals(2, repo.countDrivers());
    }
}