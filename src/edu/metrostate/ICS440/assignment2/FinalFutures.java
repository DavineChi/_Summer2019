package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FinalFutures implements Callable<Queue<WeatherData>> {
	
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
	
	@Override
	public Queue<WeatherData> call() throws Exception {
		
		return null;
	}
	
	public static void run() {
		
		
	}
	
	private static void addFinalFutures(Callable<Queue<WeatherData>> callable) {
		
		for (int i = 0; i < 4; i++) {
			
			Future<Queue<WeatherData>> future = executor.submit(callable);
			
			list.add(future);
		}
	}
}
