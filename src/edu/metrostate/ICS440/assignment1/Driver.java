package edu.metrostate.ICS440.assignment1;

/****************************************************************************************************************
 * Main class from where program execution begins.
 * <p>
 * 
 * <p>
 * Begin Date:	2019.05.23
 * <p>
 * Due Date:	2019.06.06
 */
public class Driver {
	
	public static void main(String[] args) {
		
		Queue<Integer> collection = new Queue<Integer>();
		
		for (int index = 0; index < 10000; index++) {
			
			int candidate = ((int)(Math.random() * 10000)) % 5;
			Integer integ = new Integer(candidate);
			
			collection.enqueue(integ);
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		RequestProcessor processor = new RequestProcessor(collection);
		int numberOfThreads = 5;
		
		Thread[] threads = new Thread[numberOfThreads];
		
		for (int index = 0; index < threads.length; index++) {
			
			threads[index] = new Thread(processor);
			threads[index].start();
		}
		
		try {
			
			for (int index = 0; index < threads.length; index++) {
				
				threads[index].join();
			}
		}
		
		catch (InterruptedException ie) {
			
			ie.printStackTrace();
		}
		
		ThreadStatisticsSetup.print();
	}
}