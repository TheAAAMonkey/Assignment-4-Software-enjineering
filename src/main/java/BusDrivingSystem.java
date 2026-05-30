
import java.util.ArrayList;

public class BusDrivingSystem {
    public static void main(String[] args){
        ArrayList<String> list = new ArrayList<>();

        list.add("343^5#78BS");
        //Driver driver = new Driver();
        //Bus bus = new Bus();
        BusRepository busData = new BusRepository();
        DriverRepository driverData = new DriverRepository();

        busData.addBus(new Bus("00000001", 100, 0, "Diesel"));
        busData.addBus(new Bus("00000002", 80, 0, "Diesel"));
        busData.addBus(new Bus("00000003", 60, 0, "Diesel"));
        busData.addBus(new Bus("00000004", 46, 0, "Unleaded"));
        busData.addBus(new Bus("00000005", 30, 0, "Unleaded"));

        //System.out.println(driver.getAllData());
        //System.out.println(driver.getSatisfaction());
    }
    
}
