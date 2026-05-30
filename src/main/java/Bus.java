import java.util.List;
public class Bus{
    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType; //diesel, hybrid, electric
    private boolean satisfied = true;



//constrcutor
public Bus(String busID, int capacity, double fuelLevel, String fuelType, List<String> existingIDs){

//verifying bus exist and constructing it
if (validateBusID(busID, existingIDs) == false){
    satisfied = false;
}

if (satisfied){ //busID exists
this.busID = busID;
this.capacity = capacity;
this.fuelLevel = fuelLevel;
this.fuelType = fuelType;
}else{
    this.busID = null;
}
}
//B1 IDs must be unique, 8 char long, no digits
private boolean validateBusID(String busID, List<String> existingIDs){
// check length ; ID must be exactly 8 characters long
if(busID.length() != 8){
    return false;
}

//checking characters; all must be digits
for (char c : busID.toCharArray()){
    if (Character.isDigit(c) == false){
        return false;
    }
}

//checking its unique
for (String id : existingIDs) {
    if (id.equals(busID)) {
        return false;

}
}
return true;
}

//B2 capacity cannot increase during update operations, but can decrease
public boolean updateCapacity(int newCapacity){
    if (newCapacity > this.capacity){
        return false;
    }else{
    this.capacity = newCapacity;
    }
    return true;
}
//b3,4,5
public boolean canDriverOperate(Driver driver) {
    // B3: drivers older than 50 cannot drive buses with capacity 50+
    String birthdate = driver.getBirthday();
    if (birthdate != null) {
        if (getDriverAge(birthdate) > 50 && this.capacity >= 50) {
            return false;
        }
    }
//B4 only drivers with >= 5 years of experience can drive electric buses
if (this.fuelType.equals("Electricity") && (driver.getExperienceyears() < 5)){
    return false;
}
//B5 only drivers with heavy or public transport licences are able to operate electric or hybrid buses
if (this.fuelType.equals("Electricity") || this.fuelType.equals("Hybrid")){
    String licence = driver.getLicenseType();
    if (!licence.equals("Heavy") && !licence.equals("PublicTransport")){
        return false;
    }
}

return true;
}

// helper: calculate age from birthdate DD-MM-YYYY
private int getDriverAge(String birthdate) {
    // get birth year from last 4 characters of DD-MM-YYYY
    int birthYear = Integer.parseInt(birthdate.substring(6, 10));
    
    // get current year
    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    
    // calculate age
    return currentYear - birthYear;
}

 // getters
public String getBusID() { 
    return busID; 
    }
public int getCapacity() {
    return capacity; 
    }
public double getFuelLevel() {
    return fuelLevel; 
    }
public String getFuelType() {
    return fuelType; 
    }
public boolean getSatisfied() {
    return satisfied; 
    }
}


