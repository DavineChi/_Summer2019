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

/****************************************************************************************************************
 * This program prompts the user for search criteria including a starting year and month, and ending year and
 * month, and asks whether minimum or maximum temperatures should be searched. It then returns the resulting
 * data set or weather data corresponding to the given search parameters.
 * <p>
 * 
 * The operations of this program are as follows:</br>
 * <p>
 * 
 * 1.  First, a thread pool is created. Steps 2 and 4 below support multithreaded processing</br>
 * 2.  Each weather data file is processed and searched individually by its own Callable/Future using the query
 *     provided by the user. The results from this process include the top (or bottom) results that each Future
 *     returns. For example: There are 1,218 weather data files, so there are potentially 1,218 individual lists
 *     of "top" or "bottom" results.</br>
 * 3.  Next, these individual lists are joined together into one list after each Future completes its task.</br>
 * 4.  Once all results from the initial pass have been joined together into one list, the list is then divided
 *     into 4 smaller lists, and another search for the top or bottom results is performed on each of these lists 
 *     by its own Callable/Future (e.g. - only 4 Callables/Futures are used in this step).</br>
 * 5.  The results of this second pass are joined together into a single list, and this list is then searched for
 *     the top or bottom results using only a single execution thread.</br>
 * 6.  The program displays the user-provided input parameters along with the results of the search, including the
 *     associated station information for each weather data record.</br>
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class WeatherApp {
	
	// This list will store all of the Futures from the first search pass.
	private static List<Future<ConcurrentLinkedQueue<WeatherData>>> initialList = new ArrayList<Future<ConcurrentLinkedQueue<WeatherData>>>();
	
	// This list will store the four Futures from the second search pass.
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
	
	private static FileProcessor processor = null;         // Processor for the first pass.
	private static FinalProcessor finalProcessor = null;   // Processor for the second pass.
	
	// **********************************************************************************************************
	// Private helper method to get search input parameters from the user.
	// 
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
	
	/************************************************************************************************************
	 * This method will print the input parameters that the user has entered.
	 * 
	 * @postcondition
	 *   TODO
	 */
	public static void printInputs() {

		System.out.println();
		System.out.println("  Start year: " + startYear);
		System.out.println("    End year: " + endYear);
		System.out.println(" Start month: " + startMonth);
		System.out.println("   End month: " + endMonth);
		System.out.println("Temperatures: " + element);
	}
	
	/************************************************************************************************************
	 * This method will display the final results of the search query including station information.
	 * <p>
	 * 
	 * @param searchResults
	 *   the list of top (or bottom) results to display
	 * 
	 * @param stations
	 *   the station list to match with the corresponding weather data
	 * 
	 * @postcondition
	 *   The search query results have been displayed, including corresponding station information.
	 */
	public static void printResults(ConcurrentLinkedQueue<WeatherData> searchResults, ConcurrentLinkedQueue<StationData> stations) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		System.out.println();
		
		Iterator<WeatherData> iterator = searchResults.iterator();
		
		// Iterate through the searchResults list.
		while (iterator.hasNext()) {
			
			WeatherData weatherData = iterator.next();
			
			// Iterate through the stations list.
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
		
		// Process each .dly weather data file on the file system.
		for (File file : weatherFiles) {
			
			// Create a new processor using this file and the given query.
			processor = new FileProcessor(file, query);
			
			// Step #1:
			// Add a new Future and submit it for each weather data file.
			Future<ConcurrentLinkedQueue<WeatherData>> future = processor.process();
			
			if (future != null) {
				
				// Collect the Future in a list for later processing.
				initialList.add(future);
			}
		}
	}
	
	// **********************************************************************************************************
	// Private helper method to wait for all futures to compute and return their results.
	// 
	private static void getFutures() {
		
		try {
			
			// Process each Future in the initial processing list.
			for (Future<ConcurrentLinkedQueue<WeatherData>> future : initialList) {
				
				if (future.get() != null) {
					
					for (WeatherData item : future.get()) {
						
						// Step #2:
						// Consolidate the query results from all the files into one list.
						if (item != null) {
							
							// If this WeatherData item is not null, add it to the resultQueue.
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
		int splitSize = (int)Math.ceil(queueSize / Constants.FINAL_FUTURES); // Divide the results size by 4.
		
		// Loop 4 times to create 4 Futures, adding each of the 4 Futures to the finalsList collection.
		for (int futureNumber = 1; futureNumber <= Constants.FINAL_FUTURES; futureNumber++) {
			
			splitQueue = new ConcurrentLinkedQueue<WeatherData>();
			
			// Keep polling items of the resultQueue and adding them to a smaller splitQueue.
			for (int queueIndex = 0; (resultQueue.peek() != null) && (queueIndex < splitSize); queueIndex++) {
				
				WeatherData nextItem = resultQueue.poll();
				splitQueue.add(nextItem);
			}
			
			// Instantiate a new FinalProcessor, passing in a smaller splitQueue and the initial query.
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
	
	/************************************************************************************************************
	 * This method will analyze weather data files on local storage using input from the user. This is the first
	 * pass of the overall search. One Future is created for each file processed by this method's invokation.
	 * <p>
	 * 
	 * @postcondition
	 *   An initial search pass has transpired and the top results from each file on local storage have been
	 *   collected and await further processing.
	 */
	public static void processInitialFutures() {
		
		WeatherApp.addFutures();
		WeatherApp.getFutures();
		
		processor.shutdownExecutor();
	}
	
	/************************************************************************************************************
	 * TODO
	 * <p>
	 * 
	 * @postcondition
	 *   
	 */
	public static void processFinalFutures() {
		
		WeatherApp.addFinalFutures();
		WeatherApp.getFinalFutures();
		
		finalProcessor.shutdownExecutor();
	}
	
	/************************************************************************************************************
	 * This method will initiate the search program.
	 * <p>
	 * 
	 * @postcondition
	 *   A search has been executed.
	 */
	public static void run() {
		
		Debug.enabled(false);
		
		WeatherApp.getProgramInput();
		
		weatherFiles = FileManager.getWeatherFilesQueue("ghcnd_hcn");
		stationFile = FileManager.getStationFile("ghcnd_hcn", "ghcnd-stations.txt");
		
		WeatherApp.processInitialFutures();
		WeatherApp.processFinalFutures();
		
		// Retrieve the top results from the finalQueue list (this is the third and final search pass).
		ConcurrentLinkedQueue<WeatherData> endingResults = query.retrieveResults(finalQueue, Constants.QUERY_RESULT_SIZE);
		
		// Only return those stations that are found in the endingResults list.
		ConcurrentLinkedQueue<StationData> stationsList = StationData.search(stationFile, endingResults);
		
		WeatherApp.printInputs();
		WeatherApp.printResults(endingResults, stationsList);
		System.out.println("Search complete.");
	}
}
