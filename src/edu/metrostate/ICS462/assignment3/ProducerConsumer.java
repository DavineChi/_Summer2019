package edu.metrostate.ICS462.assignment3;

import java.io.File;
import java.io.FileWriter;

/****************************************************************************************************************
* ICS 462 SUMMER 2019
* Programming Assignment 3
* Shannon Fisher
* 06/04/2019
* 
* This program expands on the producer consumer problem by adding a fixed-size buffer,
* shared between the producer and consumer threads.
****************************************************************************************************************/

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumer {

	private static final String OUTPUT_FILENAME = "Fisher_Shannon_ProgAssign3.txt";
	
	private static final int MIN_WAIT_PRODUCER = 1000;
	private static final int MAX_WAIT_PRODUCER = 5000;
	
	private static final int MIN_WAIT_CONSUMER = 2000;
	private static final int MAX_WAIT_CONSUMER = 4000;
	
	private static final int WAIT_OTHER_THREAD = 1000;
	
	private int[] data;        // The shared circular buffer.
	private int producerIndex; // Points to the last item placed in the buffer by the producer.
	private int consumerIndex; // Points to the last item removed from the buffer by the consumer.
	private int complete;      // Completion flag to indicate processing is complete.
	
	private StringBuilder stringBuilder;
	
	/************************************************************************************************************
	 * Constructor used to create a new ProducerConsumer object to illustrate the producer comsumer problem
	 * in thread concurrency.
	 * <p>
	 * 
	 * @postcondition
	 *   A new ProducerConsumer object has been created.
	 */
	public ProducerConsumer() {
		
		this.data = new int[5];
		this.producerIndex = 0;  // Points to the last item placed in the buffer by the producer.
		this.consumerIndex = 0;  // Points to the last item removed from the buffer by the consumer.
		this.complete = 0;
		
		this.stringBuilder = new StringBuilder();
		
		initStringBuilder();
	}
	
	// Private helper method to populate the assignment output header.
	private void initStringBuilder() {
		
		stringBuilder.append("Shannon Fisher" + "\n");
		stringBuilder.append("ICS 462 Programming Assignment 3" + "\n\n");
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
		
		// Iterate 100 times to produce a series of values
		// for the shared buffer.
		for (int i = 0; i < 100; i++) {
			
			// If the producer and consumer indices are NOT equivalent,
			// then wait for the producer to produce some values.
			while (producerIndex != consumerIndex) {
				
				Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
			}
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT_PRODUCER, MAX_WAIT_PRODUCER + 1));
			
			// Either the consumer has consumed all available values in
			// the shared buffer, or the producer has yet to start producing
			// values. In either case, the buffer is not full and processing
			// is not yet complete, so let's start producing (more) values
			// and store them in the shared buffer at the specified index.
			data[producerIndex] = i;
			
			// Advance the producer index using the modulus formula at
			// https://en.wikipedia.org/wiki/Circular_buffer to ensure
			// buffer wrap-around takes place.
			producerIndex = Math.floorMod((producerIndex + 1), data.length);
		}
		
		complete = 1;
	}
	
	/************************************************************************************************************
	 * Method to consume the data stored in the shared resource memory location.
	 * <p>
	 * 
	 * @postcondition
	 *   The data in the shared resource has been read.
	 * 
	 * @throws InterruptedException
	 *   InterruptedException is thrown if a thread is interrupted before or during its activity.
	 */
	public void consume() throws InterruptedException {
		
		// Continue looping until the completion flag is set.
		while (complete == 0) {
			
			// If the producer and consumer indices are equivalent,
			// then wait for the producer to produce some values.
			while (producerIndex == consumerIndex) {
				
				Thread.sleep(ThreadLocalRandom.current().nextInt(WAIT_OTHER_THREAD, WAIT_OTHER_THREAD + 1));
				System.out.println("Consumer waiting...");
				stringBuilder.append("Consumer waiting...\r\n");
			}
			
			// The producer must have produced values and stored
			// them in the buffer, so let's consume some values.
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT_CONSUMER, MAX_WAIT_CONSUMER + 1));
			System.out.println(data[consumerIndex]);
			stringBuilder.append(data[consumerIndex] + "\r\n");
			
			// Advance the consumer index using the modulus formula at
			// https://en.wikipedia.org/wiki/Circular_buffer to ensure
			// buffer wrap-around takes place.
			consumerIndex = Math.floorMod((consumerIndex + 1), data.length);
		}
		
		System.out.println("Consumer done.");
		stringBuilder.append("Consumer done.");
	}
	
	/************************************************************************************************************
	 * Method to write the program results to a file.
	 * <p>
	 * 
	 * @postcondition
	 *   The results of the shared resource consumption are output to a file on local storage.
	 * 
	 * @throws IOException
	 *   IOException is thrown if there is an I/O problem with the results file output.
	 */
	public void writeToFile() throws IOException {
		
		// File printing and output setup.
		File file = new File(OUTPUT_FILENAME);
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = null;
		
		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
			}
			
			printWriter = new PrintWriter(fileWriter);
			
			// Print the contents of the results to the output file.
			printWriter.println(stringBuilder.toString());
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
