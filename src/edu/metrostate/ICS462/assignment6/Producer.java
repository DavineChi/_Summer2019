package edu.metrostate.ICS462.assignment6;

import java.util.concurrent.ThreadLocalRandom;

/****************************************************************************************************************
 * This class handles the production of cylinder requests.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.07.10
 * <p>
 * Due Date:	2019.07.16
 */
public class Producer implements Runnable {
	
	private static final int RANGE_MINIMUM = 0;
	private static final int RANGE_MAXIMUM = 5000;
	
	private static boolean complete = false;
	
	private Requests requests;
	
	/************************************************************************************************************
	 * Constructor for a new Producer.
	 * <p>
	 * 
	 * @param requests
	 *   the Requests object upon which this Producer will operate
	 * 
	 * @postcondition
	 *   A new Producer object has been created with the specified Requests object.
	 */
	public Producer(Requests requests) {
		
		this.requests = requests;
	}
	
	/************************************************************************************************************
	 * Boolean method to check if this Producer's activity is complete.
	 * <p>
	 * 
	 * @return
	 *   True if this Producer is complete, false otherwise.
	 */
	public static boolean isComplete() {
		
		return complete;
	}
	
	@Override
	public void run() {
		
		for (int i = 0; i < 25; i++) {
			
			int candidate = ThreadLocalRandom.current().nextInt(RANGE_MINIMUM, RANGE_MAXIMUM);
			Integer integer = new Integer(candidate);
			
			requests.add(integer);
			
			System.out.println("Cylinder request produced: " + integer);
			
			try {
				
				Thread.sleep(Driver.DELAY_BETWEEN_REQUESTS);
			}
			
			catch (InterruptedException ex) {
				
				ex.printStackTrace();
			}
		}
		
		complete = true;
	}
}
