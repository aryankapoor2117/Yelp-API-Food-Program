package PA3;

public class DeliveryThread extends Thread {

    private RestaurantDrivers drivers;
    private String itemName;
    private String startLoc;

    public DeliveryThread(RestaurantDrivers drivers, String itemName, String startLoc) {
        this.drivers = drivers;
        this.itemName = itemName;
        this.startLoc = startLoc;
    }

    @Override
    public void run() {
        drivers.deliver(itemName, startLoc);
    }
}
