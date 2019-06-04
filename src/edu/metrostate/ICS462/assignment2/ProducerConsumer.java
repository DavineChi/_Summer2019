/****************************************************************************************************************
* ICS 462 SUMMER 2019
* Programming Assignment 2
* Shannon Fisher
* 06/02/2019
* 
* Website Citations...
* Creating threads:
* https://dzone.com/articles/the-evolution-of-producer-consumer-problem-in-java
* 
* This program illustrates the producer consumer problem in thread concurrency.
****************************************************************************************************************/

package edu.metrostate.ICS462.assignment2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumer {
	
	private static final String OUTPUT_FILENAME = "Fisher_Shannon_ProgAssign2.txt";
	
	private static final int MIN_WAIT = 1000;
	private static final int MAX_WAIT = 3000;
	
	private int shared;
	private int sum;
	
	/************************************************************************************************************
	 * Constructor used to create a new ProducerConsumer object to illustrate the producer comsumer problem
	 * in thread concurrency.
	 * <p>
	 * 
	 * @postcondition
	 *   A new ProducerConsumer object has been created.
	 */
	public ProducerConsumer() {
		
		shared = 100;
		sum = 0;
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
	public void produce() throws InterruptedException {
		
		for (int i = 0; i <= 4; i++) {
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT + 1));
			
			// Set the shared resource's value to the loop iteration number.
			shared = i;
		}
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
	public void consume() throws InterruptedException {
		
		for (int i = 0; i <= 4; i++) {
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT + 1));
			
			// Compute the sum of the value in the shared resource through each loop iteration.
			sum = sum + shared;
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
			sb.append("ICS 462 Programming Assignment 2" + "\n");
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
		
		ProducerConsumer pc = new ProducerConsumer();
		
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
