package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Example implements Callable<Integer> {
	
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
	
	private static List<Future<Integer>> list = new ArrayList<Future<Integer>>();
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	
	@Override
	public Integer call() throws Exception {
		
		return threadId.get();
	}
	
	private static void addFutures(Callable<Integer> callable) {
		
		for (int i = 0; i < 100; i++) {
			
			Future<Integer> future = executor.submit(callable);
			
			list.add(future);
		}
	}
	
	private static void getFutures() {
		
		try {
			
			for (Future<Integer> future : list) {
				
				System.out.println(future.get());
			}
		}
		
		catch (InterruptedException | ExecutionException ex) {
			
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Integer mySum;
		
		Callable<Integer> callable = new Example();
		
		addFutures(callable);
		getFutures();
		executor.shutdown();
		System.out.println("Done.");
	}
}
