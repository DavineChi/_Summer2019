package edu.metrostate.ICS462.assignment6;

import java.util.Vector;

/****************************************************************************************************************
 * This class handles the consumption of cylinder requests.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.07.10
 * <p>
 * Due Date:	2019.07.16
 */
public class Consumer implements Runnable {
	
	private Requests requests;
	
	/************************************************************************************************************
	 * Constructor for a new Consumer.
	 * <p>
	 * 
	 * @param requests
	 *   the Requests object upon which this Consumer will operate
	 * 
	 * @postcondition
	 *   A new Consumer object has been created with the specified Requests object.
	 */
	public Consumer(Requests requests) {
		
		this.requests = requests;
	}
	
	@Override
	public void run() {
		
		Vector<Integer> results = null;
		
		while (!Producer.isComplete()) {
			
			results = requests.get(true);
			
			try {
				
				Thread.sleep(Driver.SLEEP_TIME);
			}
			
			catch (InterruptedException ex) {
				
				ex.printStackTrace();
			}
			
			for (Integer integer : results) {
				
				System.out.println("Cylinder request consumed: " + integer);
			}
		}
	}
}
