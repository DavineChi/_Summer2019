package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Finalist implements Callable<Queue<WeatherData>> {
	
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
	
	private static ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_POOL_SIZE);
	private static List<Future<Queue<WeatherData>>> list = new ArrayList<Future<Queue<WeatherData>>>();
	
	private static Query localQuery;
	
	private static Queue<WeatherData> result = new Queue<WeatherData>();
	private static Queue<WeatherData> paredQueue;
	
	@Override
	public Queue<WeatherData> call() throws Exception {
		
		Queue<WeatherData> result = new Queue<WeatherData>();
		WeatherData dataItem;
		String element = "";
		
		float limit = 0;
		
		for (int i = 0; i < paredQueue.size(); i++) {
			
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
		}
		
		return result;
	}
	
	private static void addFutures(Callable<Queue<WeatherData>> callable) {
		
		for (int i = 0; i < Constants.FINAL_FUTURES; i++) {
			
			Future<Queue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	private static void getFutures() {
		
		try {
			
			for (Future<Queue<WeatherData>> future : list) {
				
				int weatherDataSize = future.get().size();
				
				for (int i = 0; i < weatherDataSize; i++) {
					
					result.enqueue(future.get().dequeue());
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public static Queue<WeatherData> process(Queue<WeatherData> queue, Query query) {
		
		Callable<Queue<WeatherData>> finalCallable = new Finalist();
		
		paredQueue = queue;
		localQuery = query;
		
		Finalist.addFutures(finalCallable);
		Finalist.getFutures();
		
		executor.shutdown();
		
		return result;
	}
}