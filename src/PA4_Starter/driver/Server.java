package PA4_Starter.driver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import PA4_Starter.models.Event;
import PA3.Restaurant;
import PA3.Restaurants;
import PA3.DeliveryEngine;
import PA3.DeliveryThread;
import PA3.RestaurantDrivers;

public class Server {
	
	static String schedule;
	double latitude;
	double longitude;
	static int driverCnt=0;
	static int numDrivers;
	
 DeliveryEngine devEng= new DeliveryEngine();
	public static void main(String []args)
	{
		
		Scanner in = new Scanner(System.in);
		DeliveryEngine eng = new DeliveryEngine();
		

		// Read from JSON
		String err = null;
		String filename;
		do {
			System.out.print("What is the name of the file containing the restaurant information? ");
			filename = in.nextLine();
			err = eng.readRestaurantFile(filename);
			if (err != null) {
				System.out.println(err);
			} else {
				System.out.println("The file has been properly read.\n");
			}
		} while (err != null);

		do {
			System.out.print("What is the name of the file containing the schedule information? ");
			filename = in.nextLine();
			err = eng.readScheduleFile(filename);
			if (err != null) {
				System.out.println(err);
			} else {
				System.out.println("The file has been properly read.\n");
			}
		} while (err != null);

		// Query user location
		double myLatitude = readLatitude(in, "What is your latitude? ");
		double myLongitude = readLongitude(in, "What is your longitude? ");
		eng.setMyLatitude(myLatitude);
		eng.setMyLongitude(myLongitude);

		System.out.println("How many drivers will be in service today?");
		numDrivers=in.nextInt();
		eng.init();
		eng.start();

		
		
		in.close();
			
//			System.out.println("What is the name of the schedule file?");
//			String fileName = sc.nextLine();
//			schedule = 
//			System.out.println("What is your latitude?");
//			System.out.println("What is your longitude?");
		
		try {
			ServerSocket serverSocket = new ServerSocket(3456);
			System.out.println("Listening on port 3456. Waiting for drivers...");
			Socket socket = serverSocket.accept();
			driverCnt++;
			System.out.println("Connection from "+socket.getInetAddress());
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true)
			{
				if(driverCnt == numDrivers)
				{
					System.out.println("Starting Service");
					pw.println("All drivers have arrived! Starting service.");
					break;
				}
				else
				{
					serverSocket.accept();
					System.out.println("Connection from "+socket.getInetAddress());
					driverCnt++;
					pw.println((numDrivers-driverCnt)+ " more driver is needed before service can begin Waiting...");
					pw.flush();
					System.out.println("Waiting for "+(numDrivers-driverCnt)+" more drivers...");
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static double readLongitude(Scanner in, String prompt) {
		while (true) {
			System.out.print(prompt);
			String ans = in.nextLine();
			try {
				double lon = Double.parseDouble(ans);
				if (lon > 180 || lon < -180) {
					System.out.println("The given number is not a valid longitude.");
				} else {
					return lon;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("The given input " + ans + " is not a number.");
			} finally {
				System.out.println("");
			}
		}
	}

    
	private static double readLatitude(Scanner in, String prompt) {
		while (true) {
			System.out.print(prompt);
			String ans = in.nextLine();
			try {
				double lat = Double.parseDouble(ans);
				if (lat > 90 || lat < -90) {
					System.out.println("The given number is not a valid latitude.");
				} else {
					return lat;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("The given input " + ans + " is not a number.");
			} finally {
				System.out.println("");
			}
		}
	}

}
