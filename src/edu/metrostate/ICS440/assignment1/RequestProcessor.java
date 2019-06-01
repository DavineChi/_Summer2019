package edu.metrostate.ICS440.assignment1;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RequestProcessor<T> extends Thread {
	
	private Queue<T> collection;
	private ReentrantLock collectionLock;
	private ReentrantLock processingLock;
	private ReentrantLock tabulatorLock;
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
		this.collectionLock = new ReentrantLock();
		this.processingLock = new ReentrantLock();
		this.tabulatorLock = new ReentrantLock();
		
		ThreadStatisticsSetup.setCollectionSize(collection.size());
	}
	
	private void process() throws InterruptedException {
		
		while (!collection.isEmpty()) {
			
			Integer readItem;
			Integer index;
			
			// Read section
			Debug.beforeLock();
			collectionLock.lock();
			Debug.afterLock();

			try {
				
				Debug.lockOwner();
				
				readItem = (Integer)collection.dequeue();
			}
			
			finally {
				
				Debug.beforeUnlock();
				collectionLock.unlock();
				Debug.afterUnlock();
			}
			
			ThreadStatisticsSetup.getLocalCollection().enqueue((Integer)readItem);
			
			// Process section
			Debug.beforeLock();
			processingLock.lock();
			Debug.afterLock();

			try {
				
				Debug.lockOwner();
				
				index = (Integer)ThreadStatisticsSetup.collection.get().dequeue();
				
				ThreadStatisticsSetup.addToSummaryList((Integer)index);
			}
			
			finally {
				
				Debug.beforeUnlock();
				processingLock.unlock();
				Debug.afterUnlock();
			}
		}
	}
	
	private void tabulate() {
		
		Debug.beforeLock();
		tabulatorLock.lock();
		Debug.afterLock();
		
		List<Integer> list = ThreadStatisticsSetup.summaryList.get();
		int id = ThreadStatisticsSetup.threadId.get();
		double tabSum = 0;
		
		try {
			
			Debug.lockOwner();
			
			for (int i = 0; i < list.size(); i++) {
				
				tabSum = tabSum + list.get(i);
			}
			
			tabSum = tabSum / 100;
			
			for (int j = 0; j < list.size(); j++) {
				
				String color = Color.values()[j].toString();
				int count = list.get(j);
				
				if (color.equals("Red")) {
					
					ThreadStatisticsSetup.enqueueRedTotal(count);
				}
				
				if (color.equals("Brown")) {
					
					ThreadStatisticsSetup.enqueueBrownTotal(count);
				}
				
				if (color.equals("Yellow")) {
					
					ThreadStatisticsSetup.enqueueYellowTotal(count);
				}
				
				if (color.equals("Green")) {
					
					ThreadStatisticsSetup.enqueueGreenTotal(count);
				}
				
				if (color.equals("Blue")) {
					
					ThreadStatisticsSetup.enqueueBlueTotal(count);
				}
				
				System.out.println("Tabluator: " + id + " Count " + count + " for color " + Color.values()[j] + "=" + (count / tabSum) + "%");
			}
		}
		
		finally {
			
			Debug.beforeUnlock();
			tabulatorLock.unlock();
			Debug.afterUnlock();
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			process();
			tabulate();
		}
		
		catch (InterruptedException ex) {
			
			ex.printStackTrace();
		}
	}
}