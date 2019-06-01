package edu.metrostate.ICS440.assignment1;

import java.util.concurrent.locks.ReentrantLock;

public class RequestProcessor<T> extends Thread {
	
	private Queue<T> collection;
	private ReentrantLock collectionLock;
	private ReentrantLock processingLock;
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
		this.collectionLock = new ReentrantLock();
		this.processingLock = new ReentrantLock();
	}
	
	private void process() throws InterruptedException {
		
		while (!collection.isEmpty()) {
			
			T item;
			
			Debug.beforeLock();
			collectionLock.lock();
			Debug.afterLock();

			try {
				
				Debug.lockOwner();
				
				item = collection.dequeue();
			}
			
			finally {
				
				Debug.beforeUnlock();
				collectionLock.unlock();
				Debug.afterUnlock();
			}
			
			ThreadStatisticsSetup.get().enqueue((Integer)item);
			
			Debug.beforeLock();
			processingLock.lock();
			Debug.afterLock();

			try {
				
				Debug.lockOwner();
				
				// TODO: PROCESSING
				
			}
			
			finally {
				
				Debug.beforeUnlock();
				processingLock.unlock();
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