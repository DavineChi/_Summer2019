package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/****************************************************************************************************************
 * A class used for individual weather data file processing and searching. This class represents an initial,
 * "first-pass" search.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class FileProcessor implements Callable<ConcurrentLinkedQueue<WeatherData>> {
	
	public static final AtomicInteger nextId = new AtomicInteger(1);
	
	/************************************************************************************************************
	 * The current thread ID, as assigned by this class.
	 */
	public static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return nextId.getAndIncrement();
		}
	};
	
	private static ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_POOL_SIZE);
	
	private File file;
	private Query query;
	
	/************************************************************************************************************
	 * Constructor used to create a new FileProcessor object.
	 * <p>
	 * 
	 * @param file
	 *   the file to analyze and process
	 * 
	 * @param query
	 *   the search query used by this FileProcessor
	 * 
	 * @postcondition
	 *   A new FileProcessor object has been created.
	 */
	public FileProcessor(File file, Query query) {
		
		this.file = file;
		this.query = query;
	}
	
	@Override
	public ConcurrentLinkedQueue<WeatherData> call() throws Exception {
		
		// Search the given weather data file using the user-defined search query.
		ConcurrentLinkedQueue<WeatherData> discovered = WeatherData.search(file, query, threadId.get());
		ConcurrentLinkedQueue<WeatherData> filtered = null;
		
		// If the weather data search returned a non-empty results list, ...
		if (discovered != null) {
			
			// ... then filter that results list to the 'top' results.
			filtered = query.retrieveResults(discovered, Constants.QUERY_RESULT_SIZE);
		}
		
		return filtered;
	}
	
	/************************************************************************************************************
	 * Initiates processing of this FileProcessor's data.
	 * <p>
	 * 
	 * @return
	 *   The results from searching a single weather data file.
	 */
	public Future<ConcurrentLinkedQueue<WeatherData>> process() {
		
		Future<ConcurrentLinkedQueue<WeatherData>> result = executor.submit(this);
		
		return result;
	}
	
	/************************************************************************************************************
	 * Method to shut down this FileProcessor's ExecutorService.
	 * <p>
	 * 
	 * @postcondition
	 *   This FileProcessor's ExecutorService has been shut down.
	 */
	public void shutdownExecutor() {
		
		executor.shutdown();
	}
}
