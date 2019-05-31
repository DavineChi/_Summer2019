package edu.metrostate.ICS440.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStatisticsSetup {
	
	public static final AtomicInteger NEXT_ID = new AtomicInteger(0);
	public static final ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return NEXT_ID.getAndIncrement();
		}
	};
	
	public static final ThreadLocal<Integer> COLOR_INDEX = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return null;
		}
	};
	
	public static final ThreadLocal<List<Integer>> LOCAL_COUNT = new ThreadLocal<List<Integer>>() {
		
		@Override
		protected List<Integer> initialValue() {
			
			List<Integer> list = new ArrayList<Integer>();
			
			// Fill the list with initial values (also sets the list's dimensions).
			for (int i = 0; i < Color.values().length; i++) {
				
				list.add(0);
			}
			
			return list;
		}
	};
	
	public static final ThreadLocal<Queue<Integer>> COLLECTION = new ThreadLocal<Queue<Integer>>() {
		
		@Override
		protected Queue<Integer> initialValue() {
			
			return new Queue<Integer>();
		}
	};
	
	public static Queue<Integer> get() {
		
		return COLLECTION.get();
	}
	
	public static void print() {
		
		ReentrantLock outputLock = new ReentrantLock();
		
		outputLock.lock();
		
		try {
			
			int sum = 0;
			
			List<Integer> list = LOCAL_COUNT.get();
			
			System.out.println("  >>> OUTPUT >>> Thread ID: " + String.valueOf(THREAD_ID.get()));
			
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
