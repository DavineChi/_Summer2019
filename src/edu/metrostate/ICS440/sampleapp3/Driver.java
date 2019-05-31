package edu.metrostate.ICS440.sampleapp3;

/**
 * The class creates a collection and a bunch of threads to process the transactions.
 * It then waits until the threads are all done.
 * @author mh6624pa
 *
 */
public class Driver {
	
    public static void main(String[] args) {
    	
        TransactionCollection collection = new TransactionCollection();
        
        for (int index = 0; index < 10000; index++) {
        	
            int id = ((int)(Math.random() * 10000)) % 10;
            int stage = 0;
            double amount = Math.random() * 100000;
            int site = ((int)(Math.random() * 10000)) % 5;
            
            collection.add(new Transaction(id, stage, amount, site));
        }
        
        TransactionProcessor processor = new TransactionProcessor(collection);

        System.out.println("Input collection size: " + processor.getInputCollectionSize());
        
        int numberOfThreads = 5;
        
        /*
         * Create a bunch of threads based on the TransactionProcessor object.
         */
        Thread[] threads = new Thread[numberOfThreads];
        
        for (int index = 0; index < threads.length; index++) {
        	
            threads[index] = new Thread(processor);
            threads[index].start();
        }
        
        /* Do a join on all threads until they are all completed.
         * 
         */
        try {
        	
            for (int index = 0; index < threads.length; index++) {
            	
                threads[index].join();
            }
        }
        
        catch (InterruptedException ie) {
        	
            ie.printStackTrace();
        }
        
        System.out.println("Output collection size: " + processor.getOutputCollectionSize());
    }
}