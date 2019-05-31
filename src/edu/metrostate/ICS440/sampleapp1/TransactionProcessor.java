package edu.metrostate.ICS440.sampleapp1;

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
			
			transaction = myIterator.next();
			
			if (transaction.getStage() == 0) {
				
				transaction.setStage(1);
			}
			
			else {
				
				continue;
			}
			
			transaction.process();
			outputList.add(transaction);
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