/****************************************************************************************************************
* ICS 462 SUMMER 2019
* Programming Assignment 3
* Shannon Fisher
* 06/04/2019
* 
* This program expands on the producer consumer problem by adding a fixed-size buffer,
* shared between the producer and consumer threads.
****************************************************************************************************************/

package edu.metrostate.ICS462.assignment3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MyProducerConsumer {
	
	private static final String OUTPUT_FILENAME = "Fisher_Shannon_ProgAssign3.txt";
	
	private static final int MIN_WAIT_PRODUCER = 1000;
	private static final int MAX_WAIT_PRODUCER = 5000;
	
	private static final int MIN_WAIT_CONSUMER = 2000;
	private static final int MAX_WAIT_CONSUMER = 4000;
	
	private static final int WAIT_OTHER_THREAD = 1000;
	
	//private int[] data;
	private int sum;
	
	private Buffer buffer;
	
	private volatile int producerIndex; // Points to the last item placed in the buffer by the producer.
	private volatile int consumerIndex; // Points to the last item removed from the buffer by the consumer.
	private int complete;
	
	/************************************************************************************************************
	 * Constructor used to create a new ProducerConsumer object to illustrate the producer comsumer problem
	 * in thread concurrency.
	 * <p>
	 * 
	 * @postcondition
	 *   A new ProducerConsumer object has been created.
	 */
	public MyProducerConsumer() {
		
		this.buffer = new Buffer(5);
		this.producerIndex = 0;
		this.consumerIndex = 0;
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
			
			while (producerIndex != consumerIndex) {
				
				//Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
				;
			}
			
			if (!buffer.isFull() || buffer.peek(producerIndex) == -1) {
				
				producerIndex = buffer.add(i);
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
			
			while (producerIndex == consumerIndex) {
				
				//Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
				;
			}
			
			if (!buffer.isEmpty()) {
				
				int[] result = buffer.remove(consumerIndex);
				int value = result[0];
				
				consumerIndex = result[1];
				
				System.out.println("Consumed: " + value);
			}
		}
	}
	
	/************************************************************************************************************
	 * Method to write the program results to a file.
	 * <p>
	 * 
	 * @postcondition
	 *   The sum is output to a file on local storage.
	 * 
	 * @throws IOException
	 *   IOException is thrown if there is an I/O problem with the results file output.
	 */
	public void writeToFile() throws IOException {
		
		// File printing and output setup.
		StringBuilder sb = new StringBuilder();
		File file = new File(OUTPUT_FILENAME);
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = null;
		
		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
			}
			
			printWriter = new PrintWriter(fileWriter);
			
			sb.append("Shannon Fisher" + "\n");
			sb.append("ICS 462 Programming Assignment 3" + "\n");
			sb.append("The sum is " + sum + "\n\n");
			
			printWriter.println(sb.toString());
		}
		
		catch (IOException ex) {
			
			ex.printStackTrace();
		}
		
		finally {
			
			printWriter.close();
		}
	}
	
	/************************************************************************************************************
	 * Main method from where program execution begins.
	 * <p>
	 * 
	 * @param args
	 *   this parameter is not used
	 * 
	 * @postcondition
	 *   The producer consumer thread processes have both performed executions in their own threads.
	 * 
	 * @throws InterruptedException
	 *   InterruptedException is thrown if a thread is interrupted before or during its activity.
	 * 
	 * @throws IOException
	 *   IOException is thrown if there is an I/O problem with the results file output.
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		
		MyProducerConsumer pc = new MyProducerConsumer();
		
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
		
		// Write the assignment information and program results to a text file.
		// This file is located in the Java project's root folder.
		pc.writeToFile();
	}
}
