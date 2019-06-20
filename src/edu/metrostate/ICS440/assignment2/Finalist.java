package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Finalist implements Callable<ConcurrentLinkedQueue<WeatherData>> {
	
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
	private static List<Future<ConcurrentLinkedQueue<WeatherData>>> list = new ArrayList<Future<ConcurrentLinkedQueue<WeatherData>>>();
	
	private static Query localQuery;
	
	private static ConcurrentLinkedQueue<WeatherData> localQueue;
	private static ConcurrentLinkedQueue<WeatherData> result = new ConcurrentLinkedQueue<WeatherData>();
	
	@Override
	public ConcurrentLinkedQueue<WeatherData> call() throws Exception {
		
		String element = localQuery.getElement();
		
		switch (element) {
		
		case "TMAX":
			// TODO: implementation
			//ConcurrentLinkedQueue<WeatherData> result = Finalist.findMax();
			System.out.println("TODO: implementation");
			break;
			
		case "TMIN":
			// TODO: implementation
			
			break;
		}
		
		return null;
	}
	
	// **********************************************************************************************************
	// Private helper method to determine the largest item in the Queue.
	// 
	private static ConcurrentLinkedQueue<WeatherData> findMax() {
		
		// TODO: implementation
		
		ConcurrentLinkedQueue<WeatherData> result = new ConcurrentLinkedQueue<WeatherData>();
		WeatherData item = null;
		
		float largest = -9999.9f;
		
		Iterator<WeatherData> firstIterator = localQueue.iterator();
		Iterator<WeatherData> secondIterator = localQueue.iterator();
		
		//for (WeatherData item : localQueue) {
		while (firstIterator.hasNext()) {
		
			item = firstIterator.next();
			
			float currentItemValue = item.getValue();
			
			if (currentItemValue >= largest) {
				
				largest = currentItemValue;
			}
		}
		
		item = null;
		
		while (secondIterator.hasNext()) {
			
			item = secondIterator.next();
			
			float currentItemValue = item.getValue();
			
			if (currentItemValue == largest) {
				
				result.add(item);
			}
		}
		
		return result;
	}
	
	// **********************************************************************************************************
	// Private helper method to submit new futures to the thread pool for execution.
	// 
	private static void addFutures(Callable<ConcurrentLinkedQueue<WeatherData>> callable) {
		
		for (int i = 0; i < Constants.FINAL_FUTURES; i++) {
			
			Future<ConcurrentLinkedQueue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	// **********************************************************************************************************
	// Private helper method to wait for all futures to compute and return their results.
	// 
	private static void getFutures() {
		
		try {
			
			for (Future<ConcurrentLinkedQueue<WeatherData>> future : list) {
				
				for (int i = 0; i < future.get().size(); i++) {
					
					result.add(future.get().poll());
				}
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public static ConcurrentLinkedQueue<WeatherData> process(ConcurrentLinkedQueue<WeatherData> queue, Query query) {
		
		Callable<ConcurrentLinkedQueue<WeatherData>> finalCallable = new Finalist();
		
		localQueue = queue;
		localQuery = query;
		
		Finalist.addFutures(finalCallable);
		Finalist.getFutures();
		
		executor.shutdown();
		
		return result;
	}
}