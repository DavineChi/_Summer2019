package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main implements Callable<Queue<WeatherData>> {
	
	private static final int THREAD_POOL_SIZE = 16;
	
	private static final AtomicInteger nextId = new AtomicInteger(1);
	
	/************************************************************************************************************
	 * The current thread's ID, as assigned by this class.
	 */
	public static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return nextId.getAndIncrement();
		}
	};
	
	private static ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	private static List<Future<Queue<WeatherData>>> list = new ArrayList<Future<Queue<WeatherData>>>();

	private static Scanner input = new Scanner(System.in);

	private static int startYear;
	private static int endYear;
	private static int startMonth;
	private static int endMonth;

	private static String element;
	private static Query query;
	private static Queue<File> weatherFiles;
	
	private static Queue<WeatherData> resultQueue = new Queue<WeatherData>();
	
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

//	private static void printResults(Queue<WeatherData> queue) {
//
//		// TODO: print the maximum (or minimum) five temperatures
//		// that occurred in the range of years and months
//
//		while (queue.size() != 0) {
//
//			System.out.println(queue.dequeue().toString());
//		}
//	}

	public static void printInputs() {

		System.out.println("  Start year: " + startYear);
		System.out.println("    End year: " + endYear);
		System.out.println(" Start month: " + startMonth);
		System.out.println("   End month: " + endMonth);
		System.out.println("Temperatures: " + element);
		System.out.println();
		System.out.println("Press [Enter] to execute the search...");
		
		try {
			
			System.in.read();
		}
		
		catch (IOException ex) {
			
			ex.printStackTrace();
		}
	}
	
	@Override
	public Queue<WeatherData> call() throws Exception {
		
		return WeatherData.search(weatherFiles, query, threadId.get());
	}

	private static void addFutures(Callable<Queue<WeatherData>> callable) {
		
		for (int i = 0; i < 100; i++) {
			
			Future<Queue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}

	private static void addFinalFutures(Callable<Queue<WeatherData>> callable) {
		
		for (int i = 0; i < 4; i++) {
			
			Future<Queue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	private static void getFutures() {
		
		try {
			
			for (Future<Queue<WeatherData>> future : list) {
				
				for (int i = 0; i < future.get().size(); i++) {
					
					// Consolidate the query results from all the files into one list.
					resultQueue.enqueue(future.get().dequeue());
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	/************************************************************************************************************
	 * Main method from where program execution begins.
	 * <p>
	 * 
	 * @param args this parameter is not used
	 * 
	 * @postcondition ...
	 */
	public static void main(String[] args) {

		Callable<Queue<WeatherData>> callable = new Main();
		Callable<Queue<WeatherData>> finalCallable = new Main();

//		{
//			startYear = 1998;
//			endYear = 1998;
//			startMonth = 5;
//			endMonth = 6;
//			element = "TMIN";
//			
//			query = new Query(startYear, endYear, startMonth, endMonth, element);
//		}
		
		Main.getProgramInput();
		Main.printInputs();
		
//		File stationFile = FileManager.getStationFile("ghcnd_hcn", "ghcnd-stations.txt");
		weatherFiles = FileManager.getWeatherFilesQueue("ghcnd_hcn");

//		Queue<StationData> stationsList;

//		Queue<WeatherData> weatherDataResults = WeatherData.search(weatherFiles, query);

		Main.addFutures(callable);
		Main.getFutures();
		
		//Main.addFinalFutures(finalCallable);
		
		executor.shutdown();
		System.out.println("Done.");
	}
}
