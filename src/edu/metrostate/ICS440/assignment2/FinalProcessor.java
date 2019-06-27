package edu.metrostate.ICS440.assignment2;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/****************************************************************************************************************
 * This class provides processing functionality on a list from a previously-processed, initial search pass.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class FinalProcessor implements Callable<ConcurrentLinkedQueue<WeatherData>> {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_POOL_SIZE);
	
	private ConcurrentLinkedQueue<WeatherData> queue;
	private Query query;
	
	/************************************************************************************************************
	 * Constructor used to create a new FinalProcessor object.
	 * <p>
	 * 
	 * @param queue
	 *   the collection of items to analyze and process
	 * 
	 * @param query
	 *   the search query used by this FinalProcessor
	 * 
	 * @postcondition
	 *   A new FinalProcessor object has been created.
	 */
	public FinalProcessor(ConcurrentLinkedQueue<WeatherData> queue, Query query) {
		
		this.queue = queue;
		this.query = query;
	}
	
	@Override
	public ConcurrentLinkedQueue<WeatherData> call() throws Exception {
		
		ConcurrentLinkedQueue<WeatherData> result = query.retrieveResults(queue, Constants.QUERY_RESULT_SIZE);
		
		return result;
	}
	
	/************************************************************************************************************
	 * Initiates processing of this FinalProcessor's data.
	 * <p>
	 * 
	 * @return
	 *   The results from searching a list of weather data items.
	 */
	public Future<ConcurrentLinkedQueue<WeatherData>> process() {
		
		Future<ConcurrentLinkedQueue<WeatherData>> result = executor.submit(this);
		
		return result;
	}
	
	/************************************************************************************************************
	 * Method to shut down this FinalProcessor's ExecutorService.
	 * <p>
	 * 
	 * @postcondition
	 *   This FinalProcessor's ExecutorService has been shut down.
	 */
	public void shutdownExecutor() {
		
		executor.shutdown();
	}
}