package PA3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RestaurantDrivers {

    private int availableDriverNumber;
    private double time;
    private Lock lock = new ReentrantLock();
    private Condition driverNumCondition = lock.newCondition();
    private long initialTime = System.currentTimeMillis();

    public RestaurantDrivers(int driverNumber, double time) {
        this.availableDriverNumber = driverNumber;
        this.time = time;
    }

    public void deliver(String itemName, String startLoc) {
        lock.lock();

        try {
            while (availableDriverNumber <= 0) {
                driverNumCondition.await();
            }
            availableDriverNumber -= 1;
            lock.unlock();
            long timeDifference = (System.currentTimeMillis() - initialTime);
            Date date=new Date(timeDifference);
            DateFormat simple = new SimpleDateFormat("HH:mm:ss.SS");
            simple.setTimeZone(TimeZone.getTimeZone("UTC"));
            String d = simple.format(date);
            if(d.length() > 11)
            {
                d = d.substring(0, d.length() - 1);
            }
            System.out.println("[" + d + "]" + " Starting delivery of " + itemName
                    + " from " + startLoc + "!");
            
      //      System.out.println("[" + dateFormat.format(new Date()) + "]" + " Starting delivery of " + itemName
     //               + " from " + startLoc + "!");
            Thread.sleep((long) (time * 1000));
            timeDifference = (System.currentTimeMillis() - initialTime);
            Date date2=new Date(timeDifference);
            DateFormat simple2 = new SimpleDateFormat("HH:mm:ss.SS");
            d = simple2.format(date2);
            if(d.length() > 11)
            {
                d = d.substring(0, d.length() - 1);
            }
            System.out.println("[" + d + "]" + " Finished delivery of " + itemName
                    + " from " + startLoc + "!");
        //    System.out.println("[" + dateFormat.format(new Date()) + "]" + " Finished delivery of " + itemName
        //            + " from " + startLoc + "!");

            lock.lock();
            availableDriverNumber += 1;
            driverNumCondition.signalAll();
        } catch (InterruptedException ie) {
            System.err.println("Interrupted!");
        } finally {
            lock.unlock();
        }
    }

}
