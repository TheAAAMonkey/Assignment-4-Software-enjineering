
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BusTest {

    private ArrayList<String> emptyList;
    private ArrayList<String> existingIDs;

    @BeforeEach
    void setUp() {
        emptyList = new ArrayList<>();
        existingIDs = new ArrayList<>();
        existingIDs.add("12345678");
    }

    //B1
    // TC1: Valid busID
    @Test
    void testValidBusID() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", emptyList);
        assertTrue(bus.getSatisfied());
    }

    // TC2: busID too short
    @Test
    void testBusIDTooShort() {
        Bus bus = new Bus("1234567", 50, 80.0, "Diesel", emptyList);
        assertFalse(bus.getSatisfied());
    }

    // TC3: busID contains letter
    @Test
    void testBusIDContainsLetter() {
        Bus bus = new Bus("1234567A", 50, 80.0, "Diesel", emptyList);
        assertFalse(bus.getSatisfied());
    }

    // TC4: duplicate busID
    @Test
    void testDuplicateBusID() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", existingIDs);
        assertFalse(bus.getSatisfied());
    }
// B2

    // TC5: decrease capacity - allowed
    @Test
    void testDecreaseCapacity() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", emptyList);
        assertTrue(bus.updateCapacity(30));
    }

    // TC6: increase capacity - not allowed
    @Test
    void testIncreaseCapacity() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", emptyList);
        assertFalse(bus.updateCapacity(60));
    }

    // TC7: same capacity - allowed
    @Test
    void testSameCapacity() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", emptyList);
        assertTrue(bus.updateCapacity(50));
    }

    //B3
    // TC8: driver under 50, large bus - allowed
    @Test
    void testDriverUnder50LargeBus() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 10, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1985", emptyList);
        assertTrue(bus.canDriverOperate(driver));
    }

    // TC9: driver over 50, large bus - not allowed
    @Test
    void testDriverOver50LargeBus() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 10, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1970", emptyList);
        assertFalse(bus.canDriverOperate(driver));
    }

    // TC10: driver over 50, small bus - allowed
    @Test
    void testDriverOver50SmallBus() {
        Bus bus = new Bus("12345678", 30, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 10, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1970", emptyList);
        assertTrue(bus.canDriverOperate(driver));
    }

   //B4
    // TC11: 5+ years experience, electric bus - allowed
    @Test
    void testSufficientExperienceElectric() {
        Bus bus = new Bus("12345678", 30, 80.0, "Electricity", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(bus.canDriverOperate(driver));
    }

    // TC12: under 5 years experience, electric bus - not allowed
    @Test
    void testInsufficientExperienceElectric() {
        Bus bus = new Bus("12345678", 30, 80.0, "Electricity", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 3, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertFalse(bus.canDriverOperate(driver));
    }

    // TC13: under 5 years experience, diesel bus - allowed
    @Test
    void testInsufficientExperienceDiesel() {
        Bus bus = new Bus("12345678", 30, 80.0, "Diesel", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 3, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(bus.canDriverOperate(driver));
    }

    //B5
    // TC14: Heavy licence, electric bus - allowed
    @Test
    void testHeavyLicenceElectric() {
        Bus bus = new Bus("12345678", 30, 80.0, "Electricity", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "Heavy",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(bus.canDriverOperate(driver));
    }

    // TC15: Light licence, hybrid bus - not allowed
    @Test
    void testLightLicenceHybrid() {
        Bus bus = new Bus("12345678", 30, 80.0, "Hybrid", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "Light",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertFalse(bus.canDriverOperate(driver));
    }

    // TC16: PublicTransport licence, hybrid bus - allowed
    @Test
    void testPublicTransportLicenceHybrid() {
        Bus bus = new Bus("12345678", 30, 80.0, "Hybrid", emptyList);
        Driver driver = new Driver("23@@5678AB", "John Smith", 5, "PublicTransport",
            "12|MainSt|Melbourne|VIC|Australia", "01-01-1990", emptyList);
        assertTrue(bus.canDriverOperate(driver));
    }
}