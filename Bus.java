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


//B2 capacity cannot increase during update operations, but can decrease
//B3 drivers older than 50 cannot drive bues with a capacity of 50 or more
//B4 only drivers with >= 5 years of experience can drive electric buses
//B5 only drivers with heavy or public transport licences are able to operate electric or hybrid buses

}
}
}