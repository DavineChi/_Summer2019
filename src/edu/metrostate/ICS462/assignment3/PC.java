package edu.metrostate.ICS462.assignment3;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PC {

	private static final int MIN_WAIT_PRODUCER = 1000;
	private static final int MAX_WAIT_PRODUCER = 5000;
	
	private static final int MIN_WAIT_CONSUMER = 2000;
	private static final int MAX_WAIT_CONSUMER = 4000;
	
	private static final int WAIT_OTHER_THREAD = 1000;
	
	private int[] data;
	private int producerIndex;
	private int consumerIndex;
	private int complete;
	
	public PC() {
		
		this.data = new int[5];
		this.producerIndex = 0;
		this.consumerIndex = 0;
		this.complete = 0;
	}
	
	public void produce() throws InterruptedException {
		
		for (int i = 0; i < 100; i++) {
			
			while (producerIndex != consumerIndex) {
				
				Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
			}
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT_PRODUCER, MAX_WAIT_PRODUCER + 1));
			
			data[producerIndex] = i;
			producerIndex = Math.floorMod((producerIndex + 1), data.length);
		}
		
		complete = 1;
	}
	
	public void consume() throws InterruptedException {
		
		while (complete == 0) {
			
			while (producerIndex == consumerIndex) {
				
				Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
			}
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT_CONSUMER, MAX_WAIT_CONSUMER + 1));
			System.out.println("Consumed: " + data[consumerIndex]);
			
			consumerIndex = Math.floorMod((consumerIndex + 1), data.length);
		}
	}
	
//	private int getSize() {
//		
//		int result = 0;
//		
//		for (int i = 0; i < data.length; i++) {
//			
//			if (data[i]) {
//				
//				result++;
//			}
//		}
//		
//		return result;
//	}
	
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
