import database.DataTable;
import database.Database;

public class BusRepository {
    String filename = "Bus_Repository.txt";
    final String[] headers = {"busID", "capacity", "fuelLevel", "fuelType"};
    Database bus_dp;

    // Constructor for Bus Repository class
    public BusRepository() {
        // Initialise bus database
        bus_dp = new Database();
        // Create the file if it is not already created
        bus_dp.create(filename, headers);
        // Open database
        bus_dp.open(filename);
    }

    // Sepacial Constructor for Bus Repository class for test database
    public BusRepository(String filename) {
        // Change normal filename
        this.filename = filename;
        // Initialise bus database
        bus_dp = new Database();
        // Create the file if it is not already created
        bus_dp.create(filename, headers);
        // Open database
        bus_dp.open(filename);
    }

    // Method to add bus to database
    public void addBus(Bus bus) {
        // Check if ID already exists
        if (checkBusExists(bus.getBusID())) {
            System.out.println("error: Cannot add bus because a bus with that ID already exists. Try editing instead.");
            return;
        }
        // Check Bus is valid
        if (!isBusValid(bus)) {
            return;
        }

        // convert bus to record format of String[] and insert bus into db
        this.bus_dp.insert(busToRecord(bus));
    }

    // Method to get bus from database
    public Bus getBus(String busID) {
        // Make where clause
        String[] where = {("==" + busID), "--", "--", "--"};

        // select bus from database where
        DataTable results = bus_dp.select(where);

        // Check if a record was found
        if (results.isEmpty()) {
            System.out.println("error: busID not found in database. Returning null.");
            return null;
        }

        // Check only one record was found
        if (results.getRecordCount() > 1) {
            System.out.println("error: Duplicate busID's found in database. Returning first entry.");
            return null;
        }

        // Use the first record found.
        String[] record = results.getRecord(0);

        // Reconstruct and return bus object
        return recordToBus(record);
    }

    public void updateBus(String replaceBusID, Bus newbus) {
        // Check bus to replace exists
        if (!checkBusExists(replaceBusID)) {
            System.out.println("error: BusID to replace does not exist in database.");
            return;
        }

        if (!isBusValid(newbus)) {
            System.out.println("error: Bus to replace old is invalid.");
            return;
        }

        // Make where argument
        String[] where = {("==" + replaceBusID), "--", "--", "--"};

        // Convert bus into new record
        String[] newBusRecord = busToRecord(newbus);

        // Edit db
        bus_dp.edit(where, newBusRecord);
    }

    public int countBuses() {
        return bus_dp.getRecords();
    }

    public int getNumBuses() {
        return bus_dp.getRecords();
    }

    ////////////////////////////////////////////////////////
    /// Public Utils

    public boolean checkDuplicates(String busID) {
        // Make where argument
        String[] where = {("==" + busID), "--", "--", "--"};

        // Make and get results from query
        DataTable results = bus_dp.select(where);

        // Check if any results
        if (results.getRecordCount() > 1) {
            return true;
        }

        // otherwise return false
        return false;
    }

    public boolean checkBusExists(String busID) {
        // Make where argument
        String[] where = {("==" + busID), "--", "--", "--"};

        // Make and get results from query
        DataTable results = bus_dp.select(where);
        
        // Check if any results
        // otherwise return false
        return results.getRecordCount() > 0;
    }

    // Method to clear/delete database for testing
    public void io7834vytui453v7nyu4yw5b() {
        bus_dp.clear();
    }

    public String[] getBusIds() {
        int[] select = {bus_dp.getColumnIndex("busID")};
        DataTable results = bus_dp.select(select);
        return results.getColumn(0);
    }

    // Method to check bus is valid
    public boolean isBusValid(Bus bus) {
        // Check if ID is valid
        if (!Bus.isValid(bus.getBusID())) {
            System.out.println("error: Bus ID is invalid.");
            return false;
        }
        // Check int and double are not less than 0
        if ((bus.getCapacity() < 0) || (bus.getFuelLevel() < 0.0)) {
            System.out.println("error: Bus Capacity or Fuel level is below 0.");
            return false;
        }
        // Check if any applicable fields are null
        if (
            (bus.getFuelType().equals(null)) ||
            (bus.getFuelType().equals(""))
        ) {
            System.out.println("error: A value in bus is null.");
            return false;
        }
        return true;
    }

    ///////////////////////////////////////////////////////////
    /// Private Utils
    
    private String[] busToRecord(Bus bus) {
        // convert everything to a string
        String newBus = "" + 
                        bus.getBusID() + "," +
                        bus.getCapacity() + "," +
                        bus.getFuelLevel() + "," +
                        bus.getFuelType();

        // convert String into accepted object for database class
        return newBus.split(",");
    }

    private Bus recordToBus(String[] record) {
        // Convert strings to expected values by getIndex function of DataTable
        String  busID       = record[bus_dp.getColumnIndex("busID")];
        int     busCapacity = Integer.parseInt(record[bus_dp.getColumnIndex("capacity")]);
        double  busFuel     = Double.parseDouble(record[bus_dp.getColumnIndex("fuelLevel")]);
        String  busFuelType = record[bus_dp.getColumnIndex("fuelType")];

        return new Bus(busID, busCapacity, busFuel, busFuelType);
    }


    
}
