package edu.metrostate.ICS462.assignment6;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
	
	private static final int RANGE_MINIMUM = 0;
	private static final int RANGE_MAXIMUM = 5000;
	
	private Requests requests;
	
	public Producer(Requests requests) {
		
		this.requests = requests;
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
	}
}
