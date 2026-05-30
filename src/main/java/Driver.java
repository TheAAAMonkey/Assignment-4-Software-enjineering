
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
    private ArrayList<String> data = new ArrayList<>();

    public Driver(String inputID, String inputName, int inputYears, String inputLicense, String inputAddress, String inputBirth, ArrayList<String> listIDs){
        if(inputYears <= 10){
            updateType = false;
        }

        for(int i = 0; i < listIDs.size(); ++i)
            if (listIDs.get(i) == inputID){
                satisfired = false;
                System.out.println("ID is same as List");
            }
        
        if(inputID.length() != 10){
            satisfired = false;
            System.out.println("ID Length is wrong");
        }

        //Checking if each character of this string fits all the equirements
        else{
            int amount = 0;
            for(int i = 0; i < 10; ++i){
                if (i < 2){
                    if(Character.getNumericValue(inputID.charAt(i)) < 2){
                        satisfired = false;
                        System.out.println("Apple");
                    }

                    if(!Character.isDigit(inputID.charAt(i))){
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
                        System.out.println("Apple");
                    }
                
                    if(!Character.isUpperCase(inputID.charAt(i))){
                        satisfired = false;
                        System.out.println("Apple");
                    }

                }
            }
        }  

        //Check if birthdate is working well
        if(inputBirth.length() == 10){
            for(int i = 0; i < 10; ++i){
                if(!(i == 2 || i == 5)){
                    if (!Character.isDigit(inputBirth.charAt(i))){
                        System.out.println(inputBirth.charAt(i));
                        satisfired = false;
                        //System.out.println("Apple");
                    }
                }
                else{
                    if (inputBirth.charAt(i) != '-'){
                        satisfired = false;
                        System.out.println("Apple");
                    }
                }
            }
        }
        else{
            satisfired = false;
        }

        //System.out.println(inputBirth.substring(3, 5));
        if(Integer.parseInt(inputBirth.substring(3, 5)) > 12){
            satisfired = false;
        }
    

        //Check if address is formatted well
        String temp = "";
        int num = 0;
        for(int i = 0; inputAddress.length() > i; ++i){
            if(inputAddress.charAt(i) == '|'){
                num = num + 1;
                //System.out.println(num);
                if (num < 2){
                    //System.out.println(temp);
                    for (int j = 0; temp.length() > j; ++j){
                        //System.out.println(temp);
                        if (!Character.isDigit(temp.charAt(j))){
                            satisfired = false;
                            //System.out.println("Apple");
                        }
                    }
                }

                else{
                    for (int j = 0; temp.length() > j; ++j){
                        if (!Character.isAlphabetic(temp.charAt(j))){
                            satisfired = false;
                        }
                    }                    
                }
                temp = "";
            }
            else{
                //System.out.println(temp);
                temp = temp + inputAddress.charAt(i);
            }

        }

        if (num != 4){
            satisfired = false;
        }

        for(int i = 0; i < inputName.length(); ++i){
            if(Character.isDigit(inputName.charAt(i))){
                satisfired = false;
            }
        }

        //Final check to see if all is good. If not then Nothing is set
        if (satisfired == true){
            this.driverID = inputID;
            this.name = inputName;
            experienceYears = inputYears;
            licenseType = inputLicense;
            address = inputAddress;
            birthdate = inputBirth;   
            //System.out.println(birthdate); 

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

    public String getBirthday(){
        return birthdate;
    }

    public String getName(){
        return name;
    }

    public int getExperienceyears(){
        return experienceYears;
    }

    public String getLicenseType(){
        return licenseType;
    }

    public String getAddress(){
        return address;
    }

    public boolean getLicenseUpdateLevel(){
        return updateType;
    }

    public void setID(String inputID){
        driverID = inputID;
    }
}

