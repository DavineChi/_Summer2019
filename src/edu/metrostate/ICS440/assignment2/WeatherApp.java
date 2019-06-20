package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WeatherApp {
	
	private static List<Future<ConcurrentLinkedQueue<WeatherData>>> list = new ArrayList<Future<ConcurrentLinkedQueue<WeatherData>>>();
	private static Scanner input = new Scanner(System.in);

	private static int startYear;
	private static int endYear;
	private static int startMonth;
	private static int endMonth;	
	private static String element;
	
	private static Query query;
	
	private static ConcurrentLinkedQueue<File> weatherFiles;
	private static ConcurrentLinkedQueue<WeatherData> resultQueue = new ConcurrentLinkedQueue<WeatherData>();
	
	private static FileProcessor processor;
	
	private static void getProgramInput() {

		System.out.println(" Enter a starting year...");
		System.out.print(" > ");
		startYear = Integer.parseInt(input.nextLine());

		System.out.println(" Enter an ending year: ");
		System.out.print(" > ");
		endYear = Integer.parseInt(input.nextLine());

		System.out.println(" Enter a starting month: ");
		System.out.print(" > ");
		startMonth = Integer.parseInt(input.nextLine());

		System.out.println(" Enter an ending month: ");
		System.out.print(" > ");
		endMonth = Integer.parseInt(input.nextLine());

		System.out.println(" Select one of the following options...");
		System.out.println("   [1] Mininum temperatures");
		System.out.println("   [2] Maximum temperatures");
		System.out.print(" > ");
		element = input.nextLine().toUpperCase();

		if (element.equals("1")) {

			element = "TMIN";
		}

		else if (element.equals("2")) {

			element = "TMAX";
		}

		query = new Query(startYear, endYear, startMonth, endMonth, element);
	}
	
	public static void printInputs() {

		System.out.println("  Start year: " + startYear);
		System.out.println("    End year: " + endYear);
		System.out.println(" Start month: " + startMonth);
		System.out.println("   End month: " + endMonth);
		System.out.println("Temperatures: " + element);
		System.out.println();
		System.out.println("Press [Enter] to execute the search.");
		
		try {
			
			System.in.read();
		}
		
		catch (IOException ex) {
			
			ex.printStackTrace();
		}
	}
	
	private static void printResults(ConcurrentLinkedQueue<WeatherData> queue) {
		
		while (queue.size() != 0) {

			System.out.println(queue.poll().toString());
		}
		
		System.out.println();
	}
	
	// **********************************************************************************************************
	// Private helper method to submit new futures to the thread pool for execution.
	// 
	private static void addFutures() {
		
		System.out.println("Running now...");
		System.out.println();
		System.out.println("# of weather files: " + weatherFiles.size());
		
		// TODO: ...your program should create one future for each file, and execute that future via a callable.
		for (File file : weatherFiles) {
			
			processor = new FileProcessor(file, query);
			
			// Step #1:
			// Add a new future and submit it for each weather data file.
			Future<ConcurrentLinkedQueue<WeatherData>> future = processor.process();
			
			list.add(future);
		}
	}
	
	// **********************************************************************************************************
	// Private helper method to wait for all futures to compute and return their results.
	// 
	private static void getFutures() {
		
		try {
			
			for (Future<ConcurrentLinkedQueue<WeatherData>> future : list) {
				
				for (WeatherData item : future.get()) {
					
					// Step #2:
					// Consolidate the query results from all the files into one list.
					resultQueue.add(item);
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	/************************************************************************************************************
	 * Calling this method will initiate the search program.
	 * <p>
	 * 
	 * @postcondition
	 *   A search has been executed.
	 */
	public static void run() {
		
		//WeatherApp weatherApp = new WeatherApp();
		
		{
			startYear = 1996;
			endYear = 1998;
			startMonth = 6;
			endMonth = 8;
			element = "TMAX";
			
			query = new Query(startYear, endYear, startMonth, endMonth, element);
		}
		
//		WeatherApp.getProgramInput();
		WeatherApp.printInputs();
		
//		File stationFile = FileManager.getStationFile("ghcnd_hcn", "ghcnd-stations.txt");
		weatherFiles = FileManager.getWeatherFilesQueue("ghcnd_hcn");
		
//		Queue<StationData> stationsList;
		
		WeatherApp.addFutures();
		WeatherApp.getFutures();
		
		processor.shutdownExecutor();
		
		ConcurrentLinkedQueue<WeatherData> finalSet = Finalist.process(resultQueue, query);
//		ConcurrentLinkedQueue<WeatherData> results = WeatherData.filter(finalSet, 5);
		
//		WeatherApp.printResults(results);
		
		System.out.println("Processing complete.");
	}
}
