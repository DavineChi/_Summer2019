package edu.metrostate.ICS462.assignment3;

import java.io.IOException;

public class PC {

	private int[] data;
	private int P;
	private int C;
	private int complete;
	
	public PC() {
		
		this.data = new int[5];
		this.P = 0;
		this.C = 0;
		this.complete = 0;
	}
	
	public void produce() {
		
		for (int i = 0; i < 100; i++) {
			
			while (P != C) {
				
				;
			}
			
			data[P] = i;
			//cursor = Math.floorMod((P + 1), data.length);
			//P++;
			P = Math.floorMod((P + 1), data.length);
		}
		
		complete = 1;
	}
	
	public void consume() {
		
		while (complete == 0) {
			
			while (P == C) {
				
				;
			}
			
			System.out.println("Consumed: " + data[C]);
			//C++;
			C = Math.floorMod((C + 1), data.length);
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		PC pc = new PC();
		
		// Create a new thread for the producer.
		Thread producerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					// Attempt the execute the producer method in its own thread.
					pc.produce();
				}
				
				catch (Exception ex) {
					
					ex.printStackTrace();
				}
			}
		});
		
		// Create a new thread for the consumer.
		Thread consumerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					// Attempt the execute the consumer method in its own thread.
					pc.consume();
				}
				
				catch (Exception ex) {
					
					ex.printStackTrace();
				}
			}
		});
		
		
		// Start both threads. The order in which the threads actually start is not guaranteed.
		producerThread.start();
		consumerThread.start();
		
		// Join both threads back into a single execution thread.
		producerThread.join();
		consumerThread.join();
	}
}
