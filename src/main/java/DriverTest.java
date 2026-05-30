
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DriverTest {

    private ArrayList<String> emptyList;
    private ArrayList<String> existingIDs;

    @BeforeEach
    void setUp() {
        emptyList = new ArrayList<>();
        existingIDs = new ArrayList<>();
        existingIDs.add("343^5#78BS");
    }

    //D1 D2 D3 D4 D5
    // TC1: Valid busID
    @Test
    void testValidDriverID() {
        Driver driver = new Driver("445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D1
    // TC2: first Integer is below 2
    @Test
    void testDriverIDTooShort() {
        Driver driver = new Driver("1445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D1
    // TC3: DriverID has 1 special character
    @Test
    void testFirstDriverIDIsBelow2() {
        Driver driver = new Driver("4445!T58RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D2
    // TC4: Invalid case of a single number
    @Test
    void testDuplicateBusID() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D2
    // TC5: street number is a word
    @Test
    void testDecreaseCapacity() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "Apple|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D3
    // TC6: birthday format uses / instead of -
    @Test
    void testIncreaseCapacity() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12/11/1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D3
    // TC7: month amount is over 12
    @Test
    void testSameCapacity() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-13-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D4
    // TC8: Years of experience is at 10
    @Test
    void testDriverUnder50LargeBus() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 10, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D4
    // TC9: Years of experience is at 11
    @Test
    void testDriverOver50LargeBus() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 11, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
        assertTrue(driver.getLicenseUpdateLevel());
    }
    //D5
    // TC10: name is a number
    @Test
    void testDriverOver50SmallBus() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D5
    // TC11: Id tries to be changed
    @Test
    void testSufficientExperienceElectric() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D1
    // TC12: Is is same as one in the list
    @Test
    void testInsufficientExperienceElectric() {
        Driver driver = new Driver("343^5#78BS ", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D1
    // TC13: Id is 11 charcters long
    @Test
    void testInsufficientExperienceDiesel() {
        Driver driver = new Driver("4445!T^8RRR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D1
    // TC14: ID is 9 charcters long which isnt long enough
    @Test
    void testHeavyLicenceElectric() {
        Driver driver = new Driver("445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D1
    // TC15: last charcater is lower case
    @Test
    void testLightLicenceHybrid() {
        Driver driver = new Driver("4445!T^8Rr", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
}