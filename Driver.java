import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
    private final String driverID;
    private final String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;  
    private boolean satisfired = true; 
    private boolean updateType = true;
    private String delimiter = "\t";

    public Driver(String inputID, String inputName, int inputYears, String inputLicense, String inputAddress, String inputBirth, List<String> listIDs){
        if(inputYears > 10){
            updateType = false;
        }

        for(int i = 0; i < listIDs.size(); ++i)
            if (listIDs.get(i) == inputID){
                satisfired = false;
            }
        
        if(inputID.length() != 10){
            satisfired = false;
        }

        
        if (satisfired == true){
            this.driverID = inputID;
            this.name = inputName;
            experienceYears = inputYears;
            licenseType = inputLicense;
            address = inputAddress;
            birthdate = inputBirth;    
            String[] data = {this.driverID, this.name, str(experienceYears), licenseType, address, birthdate, updateType};
        }    
        else{
            this.driverID = null;
            this.name = null;
        }
    }

    public String getID(){
        return driverID;
    }

    public boolean getSatisfaction(){
        return satisfired;
    }
}

