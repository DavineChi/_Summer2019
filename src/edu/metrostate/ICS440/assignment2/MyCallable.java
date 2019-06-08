package edu.metrostate.ICS440.assignment2;

// Adapted from http://www.journaldev.com/1090/java-callable-future-example
// by Ryan Hankins ryan.hankins@metrostate.edu

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MyCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		
		Thread.sleep(1000);
		
		//return the thread name executing this callable task
		return new Integer((int)(Math.random() * 10));
	}

	public static void main(String[] args) {
		
		Integer mySum;
		
		// Get ExecutorService from Executors utility class, thread pool size is 10
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		// create a list to hold the Future object associated with Callable
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		
		// Create MyCallable instance
		Callable<Integer> callable = new MyCallable();
		
		for(int i = 0; i < 100; i++) {
			
			// submit Callable tasks to be executed by thread pool
			Future<Integer> future = executor.submit(callable);
			
			// add Future to the list, we can get return value using Future
			list.add(future);
		}
		
		for (Future<Integer> fut : list) {
			
			try {
				
				//print the return value of Future, notice the output delay in console
				// because Future.get() waits for task to get completed
				System.out.println(new Date() + " " + fut.get());
			}
			
			catch (InterruptedException | ExecutionException ex) {
				
				ex.printStackTrace();
			}
		}
		
		//shut down the executor service now
		executor.shutdown();
	}
}
