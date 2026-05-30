import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DriverTest {
    private ArrayList<String> existingIDs;

    @BeforeEach
    void setUp() {
        existingIDs = new ArrayList<>();
        existingIDs.add("343^5#78BS");
    }

    //D1 D2 D3 D4 D5
    // TC1: Valid All Inputs. Thus tests all inputs
    @Test
    void testValidDriverAllk() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D1
    // TC2: first Integer is below 2
    @Test
    void testFirstIntBelow2() {
        Driver driver = new Driver("1445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D1
    // TC3: DriverID has 1 special character
    @Test
    void testDriverIDHas1SpecailCharacter() {
        Driver driver = new Driver("4445!T58RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D2
    // TC4: Invalid case of a single number
    @Test
    void testAddressOnlyHasNumber() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D2
    // TC5: street number is a word
    @Test
    void testNumberIsWord() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "Apple|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D3
    // TC6: birthday format uses / instead of -
    @Test
    void testBirthdayFormatUseSlahses() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12/11/1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D3
    // TC7: month amount is over 12
    @Test
    void testMonthTotalOver12() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-13-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D4
    // TC8: Years of experience is at 10
    @Test
    void testDriverExperienceAt10() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 10, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D4
    // TC9: Years of experience is at 11
    @Test
    void testDriverExperienceAt11() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 11, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertTrue(driver.getSatisfaction());
        assertTrue(driver.getLicenseUpdateLevel());
    }
    //D5
    // TC10: name is a number
    @Test
    void testDriverNameIsNumber() {
        Driver driver = new Driver("4445!T^8RR", "12345", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D5
    // TC11: Id tries to be changed
    @Test
    void testIDIsChanged() {
        Driver driver = new Driver("4445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertDoesNotThrow(() -> {
            driver.setID("Apple");
        });

    }
    //D1
    // TC12: Is is same as one in the list
    @Test
    void testDriverIDInList() {
        Driver driver = new Driver("343^5#78BS ", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D1
    // TC13: Id is 11 charcters long
    @Test
    void testIDIsTooLong() {
        Driver driver = new Driver("4445!T^8RRR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }

    //D1
    // TC14: ID is 9 charcters long which isnt long enough
    @Test
    void testIDIsTooShort() {
        Driver driver = new Driver("445!T^8RR", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
    //D1
    // TC15: last charcater is lower case
    @Test
    void testLastCharacterInIDIsNotCapital() {
        Driver driver = new Driver("4445!T^8Rr", "Apple", 2, "Light", "12|Apple|Apple|aooke|apple", "12-11-1220", existingIDs);
        assertFalse(driver.getSatisfaction());
        assertFalse(driver.getLicenseUpdateLevel());
    }
}