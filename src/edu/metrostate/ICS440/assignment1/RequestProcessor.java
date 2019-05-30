package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class RequestProcessor<T> extends Thread {
	
	private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
	private static final ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return NEXT_ID.getAndIncrement();
		}
	};
	
	private static final ThreadLocal<List<Integer>> LOCAL_COUNT = new ThreadLocal<List<Integer>>() {
		
		@Override
		protected List<Integer> initialValue() {
			
			List<Integer> list = new ArrayList<Integer>();
			
			// Fill the list with initial values (also sets the list's dimensions).
			for (int i = 0; i < Color.values().length; i++) {
				
				list.add(0);
			}
			
			return list;
		}
	};
	
	private Queue<T> collection;
//	private Queue<T> output;
	private ReentrantLock collectionLock;
	private ReentrantLock outputLock;
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
//		this.output = new Queue<T>();
		this.collectionLock = new ReentrantLock();
		this.outputLock = new ReentrantLock();
	}
	
	private void process() throws InterruptedException {
		
		T item = null;
		
		System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: before lock                      :: PROCESSOR :: " + LocalDateTime.now());
		collectionLock.lock();
		System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: after lock                       :: PROCESSOR :: " + LocalDateTime.now());
		
		try {
			
			while (!collection.isEmpty()) {
				
				// TODO: work implementation (this is a critical section)
				System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: I have the collection.           :: PROCESSOR :: " + LocalDateTime.now());
				
				item = collection.dequeue();
				
				//Thread.sleep(10);
				
				int index = Integer.parseInt(item.toString());
				
				System.out.println();
				System.out.println("  > Color: " + Color.values()[index]);
				System.out.println();
				
				LOCAL_COUNT.get().set(index, LOCAL_COUNT.get().get(index) + 1);
			}
		}
		
		catch (Exception ex) {
			
			Thread.currentThread().interrupt();
			ex.printStackTrace();
		}
		
		finally {
			
			System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: before unlock                    :: PROCESSOR :: " + LocalDateTime.now());
			collectionLock.unlock();
			System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: I no longer have the collection. :: PROCESSOR :: " + LocalDateTime.now());
			System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: after unlock                     :: PROCESSOR :: " + LocalDateTime.now());
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			process();
			printSomething();
		}
		
		catch (InterruptedException ex) {
			
			ex.printStackTrace();
		}
	}

	private void printSomething() {
		
		outputLock.lock();
		
		try {
			
			List<Integer> list = LOCAL_COUNT.get();
			
			System.out.println("  >>> OUTPUT >>> Thread ID: " + String.valueOf(THREAD_ID.get()));
			
			for (Integer i : list) {
				
				System.out.println(i);
			}
		}
		
		finally {
			
			outputLock.unlock();
		}
	}
}