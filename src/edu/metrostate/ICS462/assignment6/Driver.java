package edu.metrostate.ICS462.assignment6;

import java.util.Random;

/****************************************************************************************************************
 * Driver class for cylinder production and consumption requests.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.07.10
 * <p>
 * Due Date:	2019.07.16
 */
public class Driver {
	
	private static final Random RANDOM = new Random();
	
	private static final int MAX = 251;
	
	public static final int DELAY_BETWEEN_REQUESTS = RANDOM.nextInt(MAX) + 100;
	public static final int SLEEP_TIME = RANDOM.nextInt(MAX) + 100;
	
	/************************************************************************************************************
	 * Main method from where program execution begins. Used here for testing and debugging.
	 * <p>
	 * 
	 * @param args
	 *   parameter not used
	 * 
	 * @throws InterruptedException
	 *   Thrown if a thread is interrupted before or during its activity.
	 * 
	 * @postcondition
	 *   Program execution has transpired.
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Requests requests = new Requests();
		
		Producer producer = new Producer(requests);
		Consumer consumer = new Consumer(requests);
		
		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);
		
		producerThread.start();
		consumerThread.start();
		
		producerThread.join();
		consumerThread.join();
	}
}
