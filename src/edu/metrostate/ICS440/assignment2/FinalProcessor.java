package edu.metrostate.ICS440.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FinalProcessor implements Callable<ConcurrentLinkedQueue<WeatherData>> {
	
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
	
	private ConcurrentLinkedQueue<WeatherData> instanceQueue;
	private Query instanceQuery;
	
	public FinalProcessor(ConcurrentLinkedQueue<WeatherData> queue, Query query) {
		
		this.instanceQueue = queue;
		this.instanceQuery = query;
	}
	
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
	
	public Future<ConcurrentLinkedQueue<WeatherData>> process() {
		
		Future<ConcurrentLinkedQueue<WeatherData>> result = executor.submit(this);
		
		return result;
	}
}