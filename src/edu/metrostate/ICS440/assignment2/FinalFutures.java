package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FinalFutures implements Callable<Queue<WeatherData>> {
	
	private static final int THREAD_POOL_SIZE = 4;
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
	
	private static Queue<WeatherData> result = new Queue<WeatherData>();
	
	private static Queue<WeatherData> paredQueue;
	
	
	@Override
	public Queue<WeatherData> call() throws Exception {
		
		Queue<WeatherData> result = new Queue<WeatherData>();
		WeatherData dataItem;
		String element = "";
		
		int threshold = 1500; // TODO: not needed?
		int counter = 0;
		float limit = 0;
		
		for (int i = 0; i < paredQueue.size(); i++) {
			
			if (counter <= threshold) {
				
				dataItem = paredQueue.dequeue();
				element = dataItem.getElement();
				
				// TMAX
				if (element.equals("TMAX")) {
					
					if (dataItem.getValue() >= limit) {
						
						limit = dataItem.getValue();
						
						result.enqueue(dataItem);
					}
				}
				
				// TMIN
				else if (element.equals("TMIN")) {
					
					if (limit == 0) {
						
						limit = dataItem.getValue();
					}
					
					if (dataItem.getValue() <= limit) {
						
						result.enqueue(dataItem);
					}
				}
				
				counter++;
			}
		}
		
		return result;
	}
	
	private static void addFutures(Callable<Queue<WeatherData>> callable) {
		
		for (int i = 0; i < 100; i++) {
			
			Future<Queue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	private static void getFutures() {
		
		try {
			
			for (Future<Queue<WeatherData>> future : list) {
				
				for (int i = 0; i < future.get().size(); i++) {
					
					// Consolidate the query results from all the files into one list.
					result.enqueue(future.get().dequeue());
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public static Queue<WeatherData> run(Queue<WeatherData> queue) {
		
		Callable<Queue<WeatherData>> finalCallable = new FinalFutures();
		
		paredQueue = queue;
		
		FinalFutures.addFutures(finalCallable);
		FinalFutures.getFutures();
		
		executor.shutdown();
		
		return result;
	}
}