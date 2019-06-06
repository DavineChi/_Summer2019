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
	
	/************************************************************************************************************
	 * Method to produce data and store it in a shared resource memory location.
	 * <p>
	 * 
	 * @postcondition
	 *   The shared resource has data written to it.
	 * 
	 * @throws InterruptedException
	 *   InterruptedException is thrown if a thread is interrupted before or during its activity.
	 */
	public void produce() {
		
		for (int i = 0; i < 100; i++) {
			
			//Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT_PRODUCER, MAX_WAIT_PRODUCER + 1));
			
			while (P != C) {
				
				//Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
				;
			}
			
			// TODO: implementation
			if (true) {

				data[P] = i;
				P++;
			}
		}
		
		complete = 1;
	} 
	
	/************************************************************************************************************
	 * Method to consume the data stored in the shared resource memory location. This method also computes a
	 * rolling sum of the data values it reads from the shared resource.
	 * <p>
	 * 
	 * @postcondition
	 *   The data in the shared resource is read and is used to compute a sum.
	 * 
	 * @throws InterruptedException
	 *   InterruptedException is thrown if a thread is interrupted before or during its activity.
	 */
	public void consume() {
		
		while (complete == 0) {
			
			//Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT_CONSUMER, MAX_WAIT_CONSUMER + 1));
			
			// TODO: implementation
			while (P == C) {
				
				//Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
				;
			}
			
			if (true) {

				System.out.println("Consumed: " + data[C]);
				C--;
			}
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
