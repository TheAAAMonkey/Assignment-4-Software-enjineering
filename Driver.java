import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Character;
import java.util.ArrayList;

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
    private String[] dataFinal;
    private List<String> data;
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
        //Checking if each character of this string fits all the equirements
        int amount = 0;
        for(int i = 0; i < 10; ++i){
            if (i < 2){
                if(Character.getNumericValue(inputID.charAt(i)) < 2){
                    satisfired = false;
                }
            }
            else if (i < 8){
                if (!Character.isLetterOrDigit(inputID.charAt(i)) && !Character.isWhitespace(inputID.charAt(i))){
                    amount = amount + 1;
                }
            }
            else{  
                if (amount < 2){
                    satisfired = false;
                }
                
                if(!Character.isUpperCase(inputID.charAt(i))){
                    satisfired = false;
                }

            }
        }

        //Check if birthdate is working well
        if(inputBirth.length() == 10){
            for(int i = 0; i < 10; ++i){
                if(i != 2 || i != 5){
                    if (!Character.isDigit(inputBirth.charAt(i))){
                        satisfired = false;
                    }
                }
                else{
                    if (inputBirth.charAt(i) != '-'){
                        satisfired = false;
                    }
                }
            }
        }
        else{
            satisfired = false;
        }

        //Check if address is formatted well

        for(int i = 0; inputAddress.length() > i; ++i){
            
        }
        //Final check to see if all is good. If not then Nothing is set
        if (satisfired == true){
            this.driverID = inputID;
            this.name = inputName;
            experienceYears = inputYears;
            licenseType = inputLicense;
            address = inputAddress;
            birthdate = inputBirth;    

            //dataFinal = (this.driverID, this.name, Integer.toString(experienceYears), licenseType, address, birthdate, String.valueOf(updateType));
            data.add(this.driverID);
            data.add(this.name);
            data.add(Integer.toString(experienceYears));
            data.add(licenseType);
            data.add(address);
            data.add(birthdate);
            data.add(String.valueOf(updateType));
            //dataFinal = data;
        }    
        else{
            this.driverID = null;
            this.name = null;
            dataFinal = new String[0];
        }
    }


    public String getID(){
        return driverID;
    }

    public boolean getSatisfaction(){
        return satisfired;
    }

    public List<String> getAllData(){
        return data;
    }
}

