package edu.metrostate.ICS440.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStatisticsSetup {
	
	private static int total = 0;
	
	public static final AtomicInteger NEXT_ID = new AtomicInteger(1);
	public static final ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return NEXT_ID.getAndIncrement();
		}
	};
	
	public static final ThreadLocal<Queue<Integer>> COLLECTION = new ThreadLocal<Queue<Integer>>() {
		
		@Override
		protected Queue<Integer> initialValue() {
			
			return new Queue<Integer>();
		}
	};
	
	public static final ThreadLocal<List<Integer>> SUMMARY_LIST = new ThreadLocal<List<Integer>>() {
		
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
	
	public static Queue<Integer> getLocalCollection() {
		
		return COLLECTION.get();
	}
	
	public static void addToSummaryList(Integer indexItem) {
		
		int i = SUMMARY_LIST.get().get(indexItem);
		
		SUMMARY_LIST.get().set(indexItem, (i + 1));
	}
	
	public static void print() {
		
		ReentrantLock printingLock = new ReentrantLock();
		int sum = 0;
		
		printingLock.lock();
		
		try {
			
			List<Integer> list = SUMMARY_LIST.get();
			
			System.out.println("==Totals==");
			
			for (int i = 0; i < Color.values().length; i++) {
				
				sum = sum + list.get(i);
				
				total = total + 0; // TODO: provide correct summation
				
				System.out.println("Color " + Color.values()[i] + " composes " + list.get(i) + " of the total.");
			}
		}
		
		finally {
			
			printingLock.unlock();
		}
	}
}
