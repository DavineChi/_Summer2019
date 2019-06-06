package edu.metrostate.ICS440.assignment1;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/****************************************************************************************************************
 * This class is used to process a generic collection of data.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.05.23
 * <p>
 * Due Date:	2019.06.06
 */
public class RequestProcessor<T> extends Thread {
	
	private Queue<T> collection;
	private ReentrantLock collectionLock;
	private ReentrantLock processingLock;
	private ReentrantLock tabulatorLock;
	
	/************************************************************************************************************
	 * Constructor used to create a new RequestProcessor object with a specified Queue&ltT&gt collection.
	 * <p>
	 * 
	 * @param collection
	 *   the collection for this RequestProcessor object to process
	 * 
	 * @postcondition
	 *   A new RequestProcessor object has been created with a specified Queue&ltT&gt collection.
	 */
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
		this.collectionLock = new ReentrantLock();
		this.processingLock = new ReentrantLock();
		this.tabulatorLock = new ReentrantLock();
		
		ThreadStatisticsSetup.setCollectionSize(collection.size());
	}
	
	// This method will process the collection by ensuring only one thread can access it.
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
	
	// This method will separate the data in each thread and print the tabulation.
	private void tabulate() {
		
		Debug.beforeLock();
		tabulatorLock.lock();
		Debug.afterLock();
		
		List<Integer> tabulationList = ThreadStatisticsSetup.summaryList.get();
		
		int threadId = ThreadStatisticsSetup.threadId.get();
		double tabSum = 0;
		
		try {
			
			Debug.lockOwner();
			
			for (int i = 0; i < tabulationList.size(); i++) {
				
				tabSum = tabSum + tabulationList.get(i);
			}
			
			tabSum = tabSum / 100;
			
			for (int j = 0; j < tabulationList.size(); j++) {
				
				String color = Color.values()[j].toString();
				int count = tabulationList.get(j);
				
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
				
				System.out.println("Tabluator: " + threadId + " Count " + count + " for color " + Color.values()[j] + "=" + (count / tabSum) + "%");
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