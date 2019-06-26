package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WeatherApp {
	
	private static List<Future<ConcurrentLinkedQueue<WeatherData>>> list = new ArrayList<Future<ConcurrentLinkedQueue<WeatherData>>>();
	private static List<Future<ConcurrentLinkedQueue<WeatherData>>> finalsList = new ArrayList<Future<ConcurrentLinkedQueue<WeatherData>>>();
	
	private static Scanner input = new Scanner(System.in);

	private static int startYear;
	private static int endYear;
	private static int startMonth;
	private static int endMonth;	
	private static String element;
	
	private static Query query;
	
	private static File stationFile;
	
	private static ConcurrentLinkedQueue<File> weatherFiles;
	private static ConcurrentLinkedQueue<WeatherData> resultQueue = new ConcurrentLinkedQueue<WeatherData>();
	private static ConcurrentLinkedQueue<WeatherData> finalQueue = new ConcurrentLinkedQueue<WeatherData>();
	
	private static FileProcessor processor = null;
	private static FinalProcessor finalProcessor = null;
	
	private static void getProgramInput() {

		System.out.println(" Enter a starting year:");
		System.out.print(" > ");
		startYear = Integer.parseInt(input.nextLine());

		System.out.println(" Enter an ending year:");
		System.out.print(" > ");
		endYear = Integer.parseInt(input.nextLine());

		System.out.println(" Enter a starting month:");
		System.out.print(" > ");
		startMonth = Integer.parseInt(input.nextLine());

		System.out.println(" Enter an ending month:");
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
		
		System.out.println("Press [Enter] to execute the search.");
		
		try {
			
			System.in.read();
		}
		
		catch (IOException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public static void printInputs() {

		System.out.println();
		System.out.println("  Start year: " + startYear);
		System.out.println("    End year: " + endYear);
		System.out.println(" Start month: " + startMonth);
		System.out.println("   End month: " + endMonth);
		System.out.println("Temperatures: " + element);
	}
	
	public static void printResults(ConcurrentLinkedQueue<WeatherData> searchResults, ConcurrentLinkedQueue<StationData> stations) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		System.out.println();
		
		Iterator<WeatherData> iterator = searchResults.iterator();
		
		while (iterator.hasNext()) {
			
			WeatherData weatherData = iterator.next();
			
			for (StationData station : stations) {
				
				String stationId = station.getId();
				
				if (weatherData.getId().equals(stationId)) {
					
					stringBuilder.append(weatherData.toString() + "\r\n");
					stringBuilder.append(station.toString() + "\r\n");
				}
			}
		}
		
		System.out.println(stringBuilder.toString());
	}
	
	// **********************************************************************************************************
	// Private helper method to submit new futures to the thread pool for execution.
	// 
	private static void addFutures() {
		
		System.out.println("Running now... (processing " + weatherFiles.size() + " weather data files)");
		System.out.println();
		
		for (File file : weatherFiles) {
			
			processor = new FileProcessor(file, query);
			
			// Step #1:
			// Add a new future and submit it for each weather data file.
			Future<ConcurrentLinkedQueue<WeatherData>> future = processor.process();
			
			if (future != null) {
				
				list.add(future);
			}
		}
	}
	
	// **********************************************************************************************************
	// Private helper method to wait for all futures to compute and return their results.
	// 
	private static void getFutures() {
		
		try {
			
			for (Future<ConcurrentLinkedQueue<WeatherData>> future : list) {
				
				if (future.get() != null) {
					
					for (WeatherData item : future.get()) {
						
						// Step #2:
						// Consolidate the query results from all the files into one list.
						if (item != null) {
							
							resultQueue.add(item);
						}
					}
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	// **********************************************************************************************************
	// Private helper method to submit new final futures to the thread pool for execution.
	// 
	private static void addFinalFutures() {
		
		ConcurrentLinkedQueue<WeatherData> splitQueue;
		
		double queueSize = resultQueue.size();
		int splitSize = (int)Math.ceil(queueSize / Constants.FINAL_FUTURES); // Divide the results list by 4.
		
		for (int futureNumber = 1; futureNumber <= Constants.FINAL_FUTURES; futureNumber++) {
			
			splitQueue = new ConcurrentLinkedQueue<WeatherData>();
			
			for (int queueIndex = 0; (resultQueue.peek() != null) && (queueIndex < splitSize); queueIndex++) {
				
				WeatherData nextItem = resultQueue.poll();
				splitQueue.add(nextItem);
			}
			
			finalProcessor = new FinalProcessor(splitQueue, query);
			Future<ConcurrentLinkedQueue<WeatherData>> future = finalProcessor.process();
			
			if (future != null) {
				
				finalsList.add(future);
			}
		}
	}
	
	// **********************************************************************************************************
	// Private helper method to wait for all final futures to compute and return their results.
	// 
	private static void getFinalFutures() {
		
		try {
			
			for (Future<ConcurrentLinkedQueue<WeatherData>> future : finalsList) {
				
				if (future.get() != null) {
					
					for (WeatherData item : future.get()) {
						
						// Step #2:
						// Consolidate the query results from all the files into one list.
						if (item != null) {
							
							finalQueue.add(item);
						}
					}
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public static void processInitialFutures() {
		
		WeatherApp.addFutures();
		WeatherApp.getFutures();
		
		processor.shutdownExecutor();
	}
	
	public static void processFinalFutures() {
		
		WeatherApp.addFinalFutures();
		WeatherApp.getFinalFutures();
		
		finalProcessor.shutdownExecutor();
	}
	
	/************************************************************************************************************
	 * Calling this method will initiate the search program.
	 * <p>
	 * 
	 * @postcondition
	 *   A search has been executed.
	 */
	public static void run(String[] args) {
		
//		{
//			startYear = 2005;
//			endYear = 2006;
//			startMonth = 1;
//			endMonth = 2;
//			element = "TMAX";
//			
//			query = new Query(startYear, endYear, startMonth, endMonth, element);
//		}
		
		if (args.length > 0 && args[0].equals("-debug")) {
			 
			Debug.enabled(true);
		}
		
		WeatherApp.getProgramInput();
		
		
		weatherFiles = FileManager.getWeatherFilesQueue("ghcnd_hcn");
		stationFile = FileManager.getStationFile("ghcnd_hcn", "ghcnd-stations.txt");
		
		WeatherApp.processInitialFutures();
		WeatherApp.processFinalFutures();
		
		ConcurrentLinkedQueue<WeatherData> endingResults = query.retrieve(finalQueue, Constants.QUERY_RESULT_SIZE);
		ConcurrentLinkedQueue<StationData> stationsList = StationData.search(stationFile, endingResults);
		
		WeatherApp.printInputs();
		WeatherApp.printResults(endingResults, stationsList);
		System.out.println("Search complete.");
	}
}
