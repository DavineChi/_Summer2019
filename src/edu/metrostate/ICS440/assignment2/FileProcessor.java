package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FileProcessor implements Callable<ConcurrentLinkedQueue<WeatherData>> {
	
	public static final AtomicInteger nextId = new AtomicInteger(1);
	
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
	
	private File file;
	private Query query;
	
	public FileProcessor(File file, Query query) {
		
		this.file = file;
		this.query = query;
	}
	
	@Override
	public ConcurrentLinkedQueue<WeatherData> call() throws Exception {
		
		ConcurrentLinkedQueue<WeatherData> discovered = WeatherData.search(file, query, threadId.get());
		ConcurrentLinkedQueue<WeatherData> filtered = null;
		
		if (discovered != null) {
			
			filtered = query.retrieve(discovered, Constants.QUERY_RESULT_SIZE);
		}
		
		return filtered;
	}
	
	public Future<ConcurrentLinkedQueue<WeatherData>> process() {
		
		Future<ConcurrentLinkedQueue<WeatherData>> result = executor.submit(this);
		
		return result;
	}
	
	public void shutdownExecutor() {
		
		executor.shutdown();
		
//		boolean result = false;
//		
//		try {
//			
//			executor.shutdown();
//			
//			if (executor.awaitTermination(60, TimeUnit.SECONDS)) {
//				
//				result = true;
//			}
//		}
//		
//		catch (InterruptedException ex) {
//			
//			ex.printStackTrace();
//		}
//		
//		return result;
	}
}
