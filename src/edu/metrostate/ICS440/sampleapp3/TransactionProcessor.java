package edu.metrostate.ICS440.sampleapp3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Processes the transactions in the collection and copies the processed transactions into 
 * a second collection.
 * 
 * @author mh6624pa
 *
 */
public class TransactionProcessor extends Thread {
	
	private TransactionCollection inputCollection;
	private TransactionCollection outputList = new TransactionCollection();
	
	private ReentrantLock collectionLock = new ReentrantLock();
	private ReentrantLock outputListLock = new ReentrantLock();
	
	/**
	 * The creator must supply the input collection.
	 * 
	 * @param list
	 */
	public TransactionProcessor(TransactionCollection list) {
		
		this.inputCollection = list;
	}
	
	/**
	 * The thread processes part of the collection: an item is processed if it is at stage 0.
	 * Note that the process method changes the stage of the transaction to 1, so it is
	 * not processed by other threads.
	 * 
	 */
	public void run() {
		
		TransactionCollection.TransactionIterator myIterator = inputCollection.iterator();
		
		while (myIterator.hasNext()) {
			
			Transaction transaction;
			
			collectionLock.lock();
			
			try {
				
				transaction = myIterator.next();
				
				if (transaction.getStage() == 0) {
					
					transaction.setStage(1);
				}
				
				else {
					
					continue;
				}
			}
			
			finally {
				
				collectionLock.unlock();
			}
			
			transaction.process();
			CollectionInstance.get().add(transaction);
		}
		
		outputListLock.lock();
		
		try {
			
			outputList.add(CollectionInstance.get());
		}
		
		finally {
			
			outputListLock.unlock();
		}
	}
	
	/**
	 * Returns the size of the inputput collection.
	 */
	public int getInputCollectionSize() {
		
		return inputCollection.size();
	}
	
	/**
	 * Returns the size of the output collection.
	 */
	public int getOutputCollectionSize() {
		
		return outputList.size();
	}
}