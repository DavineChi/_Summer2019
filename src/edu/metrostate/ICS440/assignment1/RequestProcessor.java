package edu.metrostate.ICS440.assignment1;

import java.util.concurrent.locks.ReentrantLock;

public class RequestProcessor<T> extends Thread {
	
	private Queue<T> collection;
//	private Queue<T> output;
	private ReentrantLock collectionLock;
//	private ReentrantLock processingLock;
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
//		this.output = new Queue<T>();
		this.collectionLock = new ReentrantLock();
//		this.processingLock = new ReentrantLock();
	}
	
	private void process() throws InterruptedException {
		
		// TODO: PROBLEM: One thread hogs all of the processing...
		while (!collection.isEmpty()) {
			
			T item;
			
			Debug.beforeLock();
			collectionLock.lock();
			Debug.afterLock();

			try {
				
				Debug.lockOwner();
				
				item = collection.dequeue();
				
				// TODO: PROBLEM: NullPointerException thrown here for all threads...
//				int index = Integer.parseInt(item.toString());
//				
//				System.out.println();
//				System.out.println("  > Color: " + Color.values()[index]);
//				System.out.println();
				
				ThreadStatisticsSetup.get().enqueue((Integer)item);
			}
			
			finally {
				
				Debug.beforeUnlock();
				collectionLock.unlock();
				Debug.afterUnlock();
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