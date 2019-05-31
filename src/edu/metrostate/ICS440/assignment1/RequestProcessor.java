package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;
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
		
		Queue<T>.QueueIterator iterator = collection.iterator();
		
		while (iterator.hasNext()) {
			
			T item;
			
			System.out.println("Thread ID: " + String.valueOf(Constants.THREAD_ID.get()) + " :: before lock                      :: PROCESSOR :: " + LocalDateTime.now());
			collectionLock.lock();
			System.out.println("Thread ID: " + String.valueOf(Constants.THREAD_ID.get()) + " :: after lock                       :: PROCESSOR :: " + LocalDateTime.now());

			try {
				
				System.out.println("Thread ID: " + String.valueOf(Constants.THREAD_ID.get()) + " :: I have the collection.           :: PROCESSOR :: " + LocalDateTime.now());
				
				//item = collection.dequeue();
				item = iterator.next();
				
				// TODO: PROBLEM: NullPointerException thrown here for all threads.
				int index = Integer.parseInt(item.toString());
				
				System.out.println();
				System.out.println("  > Color: " + Color.values()[index]);
				System.out.println();
				
				Constants.LOCAL_COUNT.get().set(index, Constants.LOCAL_COUNT.get().get(index) + 1);
			}
			
			finally {
				
				collectionLock.unlock();
			}
		}
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