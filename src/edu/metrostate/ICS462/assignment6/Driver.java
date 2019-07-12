package edu.metrostate.ICS462.assignment6;

public class Driver {
	
	/************************************************************************************************************
	 * Main method from where program execution begins. Used here for testing and debugging.
	 * <p>
	 * 
	 * @param args
	 *   parameter not used
	 * 
	 * @postcondition
	 *   Program execution has transpired.
	 */
	public static void main(String[] args) {
		
		Requests requests = new Requests();
		
		Producer producer = new Producer(requests);
		Consumer consumer = new Consumer(requests);
		
		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);
		
		producerThread.start();
		consumerThread.start();
	}
}
