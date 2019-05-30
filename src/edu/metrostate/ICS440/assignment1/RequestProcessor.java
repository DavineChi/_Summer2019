package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RequestProcessor<T> extends Thread {
	
	private Queue<T> collection;
//	private Queue<T> output;
	private ReentrantLock collectionLock;
	private ReentrantLock processingLock;
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
//		this.output = new Queue<T>();
		this.collectionLock = new ReentrantLock();
		this.processingLock = new ReentrantLock();
	}
	
	private void process() throws InterruptedException {
		
		System.out.println("Thread ID: " + String.valueOf(Constants.THREAD_ID.get()) + " :: before lock                      :: PROCESSOR :: " + LocalDateTime.now());
		collectionLock.lock();
		System.out.println("Thread ID: " + String.valueOf(Constants.THREAD_ID.get()) + " :: after lock                       :: PROCESSOR :: " + LocalDateTime.now());
		
		
		try {
			
			T item = null;
			
			while (!collection.isEmpty()) {
				
				processingLock.lock();
				
				try {
					
					System.out.println("Thread ID: " + String.valueOf(Constants.THREAD_ID.get()) + " :: I have the collection.           :: PROCESSOR :: " + LocalDateTime.now());
					
					item = collection.dequeue();
					
					int index = Integer.parseInt(item.toString());
					
					System.out.println();
					System.out.println("  > Color: " + Color.values()[index]);
					System.out.println();
					
					Constants.LOCAL_COUNT.get().set(index, Constants.LOCAL_COUNT.get().get(index) + 1);
				}
				
				finally {
					
					processingLock.unlock();
					
				}
			}
		}
		
		finally {
			
			collectionLock.unlock();
		}
		
//		for (int i = 0; i < 10000; i++) {
//			
//			try {
//				
//				// TODO: work implementation (this is a critical section)
//				System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: I have the collection.           :: PROCESSOR :: " + LocalDateTime.now());
//				
//				item = collection.dequeue();
//				
//				//Thread.sleep(10);
//				
//				int index = Integer.parseInt(item.toString());
//				
//				System.out.println();
//				System.out.println("  > Color: " + Color.values()[index]);
//				System.out.println();
//				
//				LOCAL_COUNT.get().set(index, LOCAL_COUNT.get().get(index) + 1);
//			}
//			
//			catch (Exception ex) {
//				
//				Thread.currentThread().interrupt();
//				ex.printStackTrace();
//			}
//			
//			finally {
//				
//				System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: before unlock                    :: PROCESSOR :: " + LocalDateTime.now());
//				collectionLock.unlock();
//				System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: I no longer have the collection. :: PROCESSOR :: " + LocalDateTime.now());
//				System.out.println("Thread ID: " + String.valueOf(THREAD_ID.get()) + " :: after unlock                     :: PROCESSOR :: " + LocalDateTime.now());
//			}
//		}
	}
	
	@Override
	public void run() {
		
		try {
			
			process();
			ThreadStatisticsSetup.print();
		}
		
		catch (InterruptedException ex) {
			
			ex.printStackTrace();
		}
	}
}