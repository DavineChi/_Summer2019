package edu.metrostate.ICS440.assignment2;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FinalProcessor implements Callable<ConcurrentLinkedQueue<WeatherData>> {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_POOL_SIZE);
	
	private ConcurrentLinkedQueue<WeatherData> queue;
	private Query query;
	
	public FinalProcessor(ConcurrentLinkedQueue<WeatherData> queue, Query query) {
		
		this.queue = queue;
		this.query = query;
	}
	
	@Override
	public ConcurrentLinkedQueue<WeatherData> call() throws Exception {
		
		ConcurrentLinkedQueue<WeatherData> result = query.retrieve(queue, Constants.QUERY_RESULT_SIZE);
		
		return result;
	}
	
	public Future<ConcurrentLinkedQueue<WeatherData>> process() {
		
		Future<ConcurrentLinkedQueue<WeatherData>> result = executor.submit(this);
		
		return result;
	}
	
	public void shutdownExecutor() {
		
		executor.shutdown();
	}
}