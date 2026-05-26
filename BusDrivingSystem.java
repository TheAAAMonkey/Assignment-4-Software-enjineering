import java.util.ArrayList;

public class BusDrivingSystem {
    public static void main(String[] args){
        ArrayList<String> list = new ArrayList<>();

        list.add("343^5#78BS");
        Driver driver = new Driver("4445!T^8RR", "Apple", 12, "Mad", "ap|Apple|Apple|aooke|apple", "12-11-1220", list);
        //Bus bus = new Bus();
        BusRepository busData = new BusRepository();
        DriverRepository driverData = new DriverRepository();

        System.out.println(driver.getAllData());
        System.out.println(driver.getSatisfaction());
        
    }
    
}
