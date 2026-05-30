
import java.util.ArrayList;

public class BusDrivingSystem {
    public static void main(String[] args){
        ArrayList<String> list = new ArrayList<>();

        list.add("343^5#78BS");
        //Driver driver = new Driver();
        //Bus bus = new Bus();
        BusRepository busData = new BusRepository();
        DriverRepository driverData = new DriverRepository();

        //System.out.println(driver.getAllData());
        //System.out.println(driver.getSatisfaction());
        DriverTest test = new DriverTest();

        test.setUp();
        test.testDriverUnder50LargeBus();
    }
    
}
