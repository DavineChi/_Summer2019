package edu.metrostate.ICS462.assignment6;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/****************************************************************************************************************
 * This class handles add and get requests for cylinders and maintains a collection of cylinders that have
 * generated access requests.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.07.01
 * <p>
 * Due Date:	2019.07.09
 */
public class Requests {
	
	private static final int RANGE_MINIMUM = 0;
	private static final int RANGE_MAXIMUM = 5000;
	
	private Vector<Integer> cylinderList;
	
	/************************************************************************************************************
	 * Constructor for a new Requests object.
	 * <p>
	 * 
	 * @postcondition
	 *   A new Requests object has been created.
	 */
	public Requests() {
		
		this.cylinderList = new Vector<Integer>();
	}
	
	/************************************************************************************************************
	 * Modifier method to add a cylinder. This activity represents a generated request for a cylinder.
	 * <p>
	 * 
	 * @param cylinder
	 *   the requeseted cylinder to be added to this Requests object
	 */
	public synchronized void add(Integer cylinder) {
		
		cylinderList.add(cylinder);
		
		// After adding a cylinder, notify all waiting threads.
		notifyAll();
	}
	
	/************************************************************************************************************
	 * Accessor method to return a list of requests that have been added but not yet retrieved.
	 * <p>
	 * 
	 * @param waitIfEmpty
	 *   if true and there are no requests, the caller waits - if false OR there are requests,
	 *   the caller does not wait
	 * @return
	 *   A list of requests that have been added but not retrieved yet.
	 */
	public synchronized Vector<Integer> get(boolean waitIfEmpty) {
		
		Vector<Integer> result = null;
		
		while (cylinderList.isEmpty() && waitIfEmpty) {
			
			try {
				
				wait();
			}
			
			catch (InterruptedException ex) {
				
				ex.printStackTrace();
			}
		}
		
		notifyAll();
		
		result = new Vector<Integer>(cylinderList);
		
		cylinderList.clear();
		
		return result;
	}
	
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
		
		int iterations = 20;
		
		for (int index = 0; index < iterations; index++) {
			
			int candidate = ThreadLocalRandom.current().nextInt(RANGE_MINIMUM, RANGE_MAXIMUM);
			Integer integer = new Integer(candidate);
			
			requests.add(integer);
		}
		
		Vector<Integer> result = requests.get(false);
		
		for (int k = 0; k < result.size(); k++) {
			
			System.out.println("Cylinder #: " + result.get(k));
		}
	}
}
