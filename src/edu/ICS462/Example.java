package edu.ICS462;

import java.util.LinkedList;
import java.util.Queue;

public class Example {
	
	private static class Buffer {
		
		Queue<Integer> list;
		int size;
		
		public Buffer(int size) {
			
			this.list = new LinkedList<Integer>();
			this.size = size;
		}
		
		public void produce() throws InterruptedException {
			
			int value = 0;
			
			while (true) {
				
				synchronized (this) {
					
					while (list.size() >= size) {
						
						this.wait();
					}
					
					list.add(value);
					
					//System.out.println("Produced value: " + value);
					
					value++;
					
					this.notify();
					
					Thread.sleep(1000);
				}
			}
		}
		
		public void consume() throws InterruptedException {
			
			while (true) {
				
				synchronized (this) {
					
					while (list.size() == 0) {
						
						this.wait();
					}
					
					int value = list.poll();
					
					//System.out.println("Consumed value: " + value);
					
					this.notify();
					
					Thread.sleep(1000);
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Buffer buffer = new Buffer(2);
		
		Thread producerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					buffer.produce();
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
					
					buffer.consume();
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
	}
}
