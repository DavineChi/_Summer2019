/**********************************************************************************
* Class: ICS 462 SUMMER 2019
* Assignment: Programming Assignment 2
* Author: Shannon Fisher
* 
* Website Citations...
* 
* Help with creating threads:
* https://dzone.com/articles/the-evolution-of-producer-consumer-problem-in-java
* 
* Program that <...>
**********************************************************************************/

package edu.ICS462;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumer {
	
	private static final int MIN_WAIT = 1000;
	private static final int MAX_WAIT = 3000;
	
	private int shared;
	private int sum;
	
	public ProducerConsumer() {
		
		shared = 100;
		sum = 0;
	}
	
	public int getSum() {
		
		return sum;
	}
	
	public void produce() throws InterruptedException {
		
		int value = 0;
		
		for (int i = 0; i < 4; i++) {
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT + 1));
			
			shared = i;
		}
	} 
	
	public void consume() throws InterruptedException {
		
		for (int i = 0; i < 4; i++) {
			
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT + 1));
			
			sum = sum + shared;
		}
	}
	
	public void writeToFile() throws IOException {
		
		StringBuilder sb = new StringBuilder();
		File file = new File("Fisher_Shannon_ProgAssign2.txt");
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = null;
		
		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
			}
			
			printWriter = new PrintWriter(fileWriter);
			
			sb.append("Shannon Fisher" + "\n");
			sb.append("ICS 462 Programming Assignment 2" + "\n");
			sb.append("The sum is " + this.getSum() + "\n\n");
			
			printWriter.println(sb.toString());
		}
		
		catch (IOException ex) {
			
			ex.printStackTrace();
		}
		
		finally {
			
			printWriter.close();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		ProducerConsumer pc = new ProducerConsumer();
		
		Thread producerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					pc.produce();
				}
				
				catch (Exception ex) {
					
					ex.printStackTrace();
				}
			}
		});
		
		Thread consumerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					pc.consume();
				}
				
				catch (Exception ex) {
					
					ex.printStackTrace();
				}
			}
		});
		
		producerThread.start();
		consumerThread.start();
		
		producerThread.join();
		consumerThread.join();
		
		pc.writeToFile();
	}
}
