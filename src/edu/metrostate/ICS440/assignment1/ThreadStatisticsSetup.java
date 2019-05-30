package edu.metrostate.ICS440.assignment1;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStatisticsSetup {

	public static void print() {
		
		ReentrantLock outputLock = new ReentrantLock();
		
		outputLock.lock();
		
		try {
			
			int sum = 0;
			
			List<Integer> list = Constants.LOCAL_COUNT.get();
			
			System.out.println("  >>> OUTPUT >>> Thread ID: " + String.valueOf(Constants.THREAD_ID.get()));
			
			for (int i = 0; i < Color.values().length; i++) {
				
				sum = sum + list.get(i);
				
				System.out.println(Color.values()[i] + ": " + list.get(i));
			}
			
			System.out.println("  >>>    SUM >>> " + sum);
		}
		
		finally {
			
			outputLock.unlock();
		}
	}
}
