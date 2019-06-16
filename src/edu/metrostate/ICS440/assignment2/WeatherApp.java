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

public class WeatherApp implements Callable<Queue<WeatherData>> {
	
	private static final int THREAD_POOL_SIZE = 12;
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
	
	private static void printResults(Queue<WeatherData> queue) {
		
		while (queue.size() != 0) {

			System.out.println(queue.dequeue().toString());
		}
		
		System.out.println();
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
	
	@Override
	public Queue<WeatherData> call() throws Exception {
		
		return WeatherData.search(weatherFiles, query, threadId.get());
	}

	private static void addFutures(Callable<Queue<WeatherData>> callable) {
		
		System.out.println("Running now...");
		System.out.println();
		
		for (int i = 0; i < 100; i++) {
			
			Future<Queue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	public static void run() {
		
		Callable<Queue<WeatherData>> callable = new WeatherApp();
		
		{
			startYear = 1998;
			endYear = 2000;
			startMonth = 6;
			endMonth = 7;
			element = "TMAX";
			
			query = new Query(startYear, endYear, startMonth, endMonth, element);
		}
		
//		WeatherApp.getProgramInput();
		WeatherApp.printInputs();
		
//		File stationFile = FileManager.getStationFile("ghcnd_hcn", "ghcnd-stations.txt");
		weatherFiles = FileManager.getWeatherFilesQueue("ghcnd_hcn");

//		Queue<StationData> stationsList;
		
		WeatherApp.addFutures(callable);
		WeatherApp.getFutures();
		
		executor.shutdown();
		
		Queue<WeatherData> finalSet = Finalist.process(resultQueue);
		Queue<WeatherData> results = WeatherData.filter(finalSet, 5);
		
		WeatherApp.printResults(results);
		
		System.out.println("Processing complete.");
	}
}
