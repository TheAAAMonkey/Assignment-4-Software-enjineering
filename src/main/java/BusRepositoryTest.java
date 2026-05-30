import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusRepositoryTest {
    private BusRepository busRepository;
    private String testDatabaseName;

    @BeforeEach
    void setUp() {        
        testDatabaseName = "Bus_Repository_Test.txt";
        busRepository = new BusRepository(testDatabaseName);

        // Dangerous method is purposly made to be non human readable
        // Deletes database, which is needed for repeatable test cases
        busRepository.io7834vytui453v7nyu4yw5b(); 
    }

    //B1
    // TC1: Insert Successful?
    @Test
    void test_Insert() {
        busRepository.addBus(new Bus("00000001", 50, 80.0, "Diesel"));
        boolean pass = fileContains(testDatabaseName, "00000001,50,80.0,Diesel");
        assertTrue(pass);
    }

    // TC2: Read Successful?
    @Test
    void test_Read() {
        Bus bus1 = new Bus("00000001", 50, 80.0, "Diesel");
        busRepository.addBus(bus1);
        Bus bus = busRepository.getBus(bus1.getBusID());
        boolean equal = (bus.getBusID().equals(bus1.getBusID())) &&
                        (bus.getCapacity() == bus1.getCapacity()) &&
                        (bus.getFuelLevel() == bus1.getFuelLevel()) &&
                        (bus.getFuelType().equals(bus1.getFuelType()));
        assertTrue(equal);
    }

    // TC3: Insert invalid bus ID
    @Test
    void test_Invalid_ID_Bus() {
        Bus invalidBus = new Bus("0000000A", 60, 100.0, "Ethanol");
        busRepository.addBus(invalidBus);
        boolean pass = !fileContains(testDatabaseName, "0000000A,60,100.0,Ethanol"); // pass = true if file does NOT contain
        assertTrue(pass);
    }

    // TC4: Insert negative fuel Level
    @Test
    void test_Invalid_fuelLevel_Bus() {
        Bus invalidBus = new Bus("00000001", 60, -100.0, "Ethanol");
        busRepository.addBus(invalidBus);
        boolean pass = !fileContains(testDatabaseName, "00000001,60,-100.0,Ethanol"); // pass = true if file does NOT contain
        assertTrue(pass);
    }

    // TC5: Input duplicate bus ID's
    @Test
    void test_Duplicate_BusID() {
        busRepository.addBus(new Bus("00000003", 80, 30.0, "Unleaded"));
        busRepository.addBus(new Bus("00000003", 120, 60.0, "Diesel"));
        boolean pass = !(fileContains(testDatabaseName, "00000003,80,30.0,Unleaded") && fileContains(testDatabaseName, "00000003,120,60.0,Diesel"));
        assertTrue(pass);
    }

    // TC6: Update bus 
    @Test
    void test_UpdateBus() {
        busRepository.addBus(new Bus("00000001", 50, 80.0, "Diesel"));
        busRepository.addBus(new Bus("00000002", 0, 0.0, "Unknown"));
        busRepository.addBus(new Bus("00000003", 80, 30.0, "Unleaded"));

        Bus updatedBus = new Bus("00000002", 60, 100.0, "Diesel");
        busRepository.updateBus("00000002", updatedBus);

        boolean pass = fileContains(testDatabaseName, "00000002,60,100.0,Diesel") && !(fileContains(testDatabaseName, "00000002,0,0.0,Unknown"));
        assertTrue(pass);
    }

    // TC7: update busID to be invalid
    @Test
    void test_Invalid_ID_Updated_Bus_Values_Are_Valid() {
        busRepository.addBus(new Bus("00000001", 50, 80.0, "Diesel"));
        busRepository.addBus(new Bus("00000002", 60, 100.0, "Diesel"));

        Bus updatedBus = new Bus("00rt0002", 60, 100.0, "Diesel");
        busRepository.updateBus("00000002", updatedBus);

        boolean pass = fileContains(testDatabaseName, "00000002,60,100.0,Diesel") && !(fileContains(testDatabaseName, "00rt0002,0,0.0,Unknown"));
        assertTrue(pass);
    }

    // TC8: update values to be negative
    @Test
    void test_Negative_Updated_Bus_Values_Are_Valid() {
        busRepository.addBus(new Bus("00000001", 50, 80.0, "Diesel"));
        busRepository.addBus(new Bus("00000002", 60, 100.0, "Diesel"));

        Bus updatedBus = new Bus("00000002", -60, -100.0, "Diesel");
        busRepository.updateBus("00000002", updatedBus);

        boolean pass = fileContains(testDatabaseName, "00000002,60,100.0,Diesel") && !(fileContains(testDatabaseName, "00000002,-60,-100.0,Unknown"));
        assertTrue(pass);
    }

    // TC9: test record count is updated correctly
    @Test
    void test_basic_record_update() {
        busRepository.addBus(new Bus("00000001", 50, 80.0, "Unleaded"));
        busRepository.addBus(new Bus("00000002", 60, 100.0, "Diesel"));
        busRepository.addBus(new Bus("00000003", 50, 80.0, "Diesel"));
        busRepository.addBus(new Bus("00000004", 89, 100.0, "Diesel"));
        busRepository.addBus(new Bus("00000005", 45, 80.0, "Unleaded"));
        busRepository.addBus(new Bus("00000006", 90, 100.0, "Diesel"));

        boolean pass = busRepository.getNumBuses() == 6;
        assertTrue(pass);
    }

    // TC10: test record count is updated correctly
    @Test
    void test_advanced_record_update() {
        busRepository.addBus(new Bus("00000001", 50, 80.0, "Unleaded"));
        busRepository.addBus(new Bus("0000000A", 60, 100.0, "Diesel"));
        busRepository.addBus(new Bus("00000003", 50, 80.0, "Diesel"));
        busRepository.addBus(new Bus("00000004", -89, 100.0, "Diesel"));
        busRepository.addBus(new Bus("00000005", 45, -80.0, "Unleaded"));
        busRepository.addBus(new Bus("00000006", 90, 100.0, "Diesel"));

        boolean pass = busRepository.getNumBuses() == 3;
        assertTrue(pass);
    }

    // Not a test, used to check
    boolean fileContains(String filename, String contains) {
        try {
            String str = Files.readString(Path.of(filename));
            return str.contains(contains);

        } catch (IOException e) {
            return false;
        }
    }

}




