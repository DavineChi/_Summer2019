package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
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
			Finalist.findMax();
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
	private static void findMax() {
		
		// TODO: implementation
		
		ConcurrentLinkedQueue<WeatherData> queueResult = new ConcurrentLinkedQueue<WeatherData>();
		
		WeatherData result = null;
		
		float largest = -9999.9f;
		
		while (!localQueue.isEmpty()) {
			
			if (localQueue.isEmpty()) {
				
				String temp = "";
			}
			
			WeatherData item = localQueue.poll();
			float currentItemValue = item.getValue();
			
			if (currentItemValue > largest) {
				
				largest = currentItemValue;
				result = item;
			}
			
			queueResult.add(result);
		}
		
		String stop = "";
	}
	
//	@Override
//	public Queue<WeatherData> call() throws Exception {
//		
//		Queue<WeatherData> result = new Queue<WeatherData>();
//		WeatherData dataItem;
//		String element = "";
//		
//		float limit = 0;
//		
//		for (int i = 0; i < paredQueue.size(); i++) {
//			
//			dataItem = paredQueue.dequeue();
//			element = dataItem.getElement();
//			
//			// TMAX
//			if (element.equals("TMAX")) {
//				
//				if (dataItem.getValue() >= limit) {
//					
//					limit = dataItem.getValue();
//					
//					result.enqueue(dataItem);
//				}
//			}
//			
//			// TMIN
//			else if (element.equals("TMIN")) {
//				
//				if (limit == 0) {
//					
//					limit = dataItem.getValue();
//				}
//				
//				if (dataItem.getValue() <= limit) {
//					
//					result.enqueue(dataItem);
//				}
//			}
//		}
//		
//		return result;
//	}
	
	private static void addFutures(Callable<ConcurrentLinkedQueue<WeatherData>> callable) {
		
		for (int i = 0; i < Constants.FINAL_FUTURES; i++) {
			
			Future<ConcurrentLinkedQueue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	private static void getFutures() {
		
		try {
			
			for (Future<ConcurrentLinkedQueue<WeatherData>> future : list) {
				
				int weatherDataSize = future.get().size();
				
				for (int i = 0; i < weatherDataSize; i++) {
					
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