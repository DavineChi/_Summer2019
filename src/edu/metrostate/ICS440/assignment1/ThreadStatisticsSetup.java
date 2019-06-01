package edu.metrostate.ICS440.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadStatisticsSetup {
	
	public static final AtomicInteger nextId = new AtomicInteger(1);
	public static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return nextId.getAndIncrement();
		}
	};
	
	public static final ThreadLocal<Queue<Integer>> collection = new ThreadLocal<Queue<Integer>>() {
		
		@Override
		protected Queue<Integer> initialValue() {
			
			return new Queue<Integer>();
		}
	};
	
	public static final ThreadLocal<List<Integer>> summaryList = new ThreadLocal<List<Integer>>() {
		
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
	
	private static Queue<Integer> totalsRed = new Queue<Integer>();
	private static Queue<Integer> totalsBrown = new Queue<Integer>();
	private static Queue<Integer> totalsYellow = new Queue<Integer>();
	private static Queue<Integer> totalsGreen = new Queue<Integer>();
	private static Queue<Integer> totalsBlue = new Queue<Integer>();
	private static Queue<Queue<Integer>> totalsFinal = new Queue<Queue<Integer>>();
	
	private static int collectionSize;
	
	public static void setCollectionSize(int size) {
		
		collectionSize = size;
	}
	
	public static void enqueueRedTotal(Integer item) {
		
		totalsRed.enqueue(item);
	}
	
	public static void enqueueBrownTotal(Integer item) {
		
		totalsBrown.enqueue(item);
	}
	
	public static void enqueueYellowTotal(Integer item) {
		
		totalsYellow.enqueue(item);
	}
	
	public static void enqueueGreenTotal(Integer item) {
		
		totalsGreen.enqueue(item);
	}
	
	public static void enqueueBlueTotal(Integer item) {
		
		totalsBlue.enqueue(item);
	}
	
	public static void enqueueTotals(Queue<Integer> queue) {
		
		totalsFinal.enqueue(queue);
	}
	
	public static Queue<Integer> getLocalCollection() {
		
		return collection.get();
	}
	
	public static void addToSummaryList(Integer indexItem) {
		
		int i = summaryList.get().get(indexItem);
		
		summaryList.get().set(indexItem, (i + 1));
	}
	
	public static void print() {
		
		int index = 0;
		
		totalsFinal.enqueue(totalsRed);
		totalsFinal.enqueue(totalsBrown);
		totalsFinal.enqueue(totalsYellow);
		totalsFinal.enqueue(totalsGreen);
		totalsFinal.enqueue(totalsBlue);
		
		System.out.println("==Totals==");
		
		while (!totalsFinal.isEmpty()) {
			
			Queue<Integer> colorQueue = totalsFinal.dequeue();
			double sum = 0;
			
			while (!colorQueue.isEmpty()) {
				
				sum = sum + colorQueue.dequeue();
			}
			
			double percent = (sum / collectionSize) * 100;
			
			System.out.println("Color " + Color.values()[index] + " composes " + String.format("%.02f", percent) + "% of the total.");
			
			index++;
		}
	}
}
