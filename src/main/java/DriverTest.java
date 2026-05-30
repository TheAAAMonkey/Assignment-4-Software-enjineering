
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

    //B1
    // TC1: Valid busID
    @Test
    void testValidDriverID() {
        Driver driver = new Driver("445!T^8RR", "Apple", 2, "Mad", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
    }

    // TC2: busID too short
    @Test
    void testDriverIDTooShort() {
        Driver driver = new Driver("1234567", 50, 80.0, "Diesel", emptyList);
        assertFalse(driver.getSatisfaction());
    }

    // TC3: busID contains letter
    @Test
    void testFirstDriverIDIsBelow2() {
        Driver driver = new Driver("1234567A", 50, 80.0, "Diesel", emptyList);
        assertFalse(driver.getSatisfaction());
    }

    // TC4: duplicate busID
    @Test
    void testDuplicateBusID() {
        Driver driver = new Driver("12345678", 50, 80.0, "Diesel", existingIDs);
        assertFalse(driver.getSatisfaction());
    }
// B2

    // TC5: decrease capacity - allowed
    @Test
    void testDecreaseCapacity() {
        Driver driver = new Driver("12345678", 50, 80.0, "Diesel", emptyList);
        assertTrue(driver.updateCapacity(30));
    }

    // TC6: increase capacity - not allowed
    @Test
    void testIncreaseCapacity() {
        Driver driver = new Driver("12345678", 50, 80.0, "Diesel", emptyList);
        assertFalse(driver.updateCapacity(60));
    }

    // TC7: same capacity - allowed
    @Test
    void testSameCapacity() {
        Driver driver = new Driver("12345678", 50, 80.0, "Diesel", emptyList);
        assertTrue(driver.updateCapacity(50));
    }

    //B3
    // TC8: driver under 50, large driver - allowed
    @Test
    void testDriverUnder50LargeBus() {
        Driver driver = new Driver("12345678", 50, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 10, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1985", emptyList);
        assertTrue(driver.canDriverOperate(driver));
    }

    // TC9: driver over 50, large driver - not allowed
    @Test
    void testDriverOver50LargeBus() {
        Driver driver = new Driver("12345678", 50, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 10, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1970", emptyList);
        assertFalse(driver.canDriverOperate(driver));
    }

    // TC10: driver over 50, small driver - allowed
    @Test
    void testDriverOver50SmallBus() {
        Driver driver = new Driver("12345678", 30, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 10, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1970", emptyList);
        assertTrue(driver.canDriverOperate(driver));
    }

   //B4
    // TC11: 5+ years experience, electric driver - allowed
    @Test
    void testSufficientExperienceElectric() {
        Driver driver = new Driver("12345678", 30, 80.0, "Electricity", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(driver.canDriverOperate(driver));
    }

    // TC12: under 5 years experience, electric driver - not allowed
    @Test
    void testInsufficientExperienceElectric() {
        Driver driver = new Driver("12345678", 30, 80.0, "Electricity", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 3, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertFalse(driver.canDriverOperate(driver));
    }

    // TC13: under 5 years experience, diesel driver - allowed
    @Test
    void testInsufficientExperienceDiesel() {
        Driver driver = new Driver("12345678", 30, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 3, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(driver.canDriverOperate(driver));
    }

    //B5
    // TC14: Heavy licence, electric driver - allowed
    @Test
    void testHeavyLicenceElectric() {
        Driver driver = new Driver("12345678", 30, 80.0, "Electricity", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(driver.canDriverOperate(driver));
    }

    // TC15: Light licence, hybrid driver - not allowed
    @Test
    void testLightLicenceHybrid() {
        Driver driver = new Driver("12345678", 30, 80.0, "Hybrid", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "Light",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertFalse(driver.canDriverOperate(driver));
    }

    // TC16: PublicTransport licence, hybrid driver - allowed
    @Test
    void testPublicTransportLicenceHybrid() {
        Driver driver = new Driver("12345678", 30, 80.0, "Hybrid", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "PublicTransport",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(driver.canDriverOperate(driver));
    }
}