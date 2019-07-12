package edu.metrostate.ICS462.assignment6;

import java.util.Vector;

public class Consumer implements Runnable {
	
	private Requests requests;
	
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
