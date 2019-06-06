package edu.metrostate.ICS440.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/****************************************************************************************************************
 * This class contains static methods and thread-local properties used for processing a collection
 * of data with multiple threads.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.05.23
 * <p>
 * Due Date:	2019.06.06
 */
public class ThreadStatisticsSetup {
	
	private static Queue<Integer> totalsRed = new Queue<Integer>();
	private static Queue<Integer> totalsBrown = new Queue<Integer>();
	private static Queue<Integer> totalsYellow = new Queue<Integer>();
	private static Queue<Integer> totalsGreen = new Queue<Integer>();
	private static Queue<Integer> totalsBlue = new Queue<Integer>();
	private static Queue<Queue<Integer>> totalsFinal = new Queue<Queue<Integer>>();
	
	private static int collectionSize;
	
	private static final AtomicInteger nextId = new AtomicInteger(1);
	
	/************************************************************************************************************
	 * The current thread's ID, as assigned by this class.
	 */
	public static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return nextId.getAndIncrement();
		}
	};
	
	/************************************************************************************************************
	 * The current thread's copy of the collection.
	 */
	public static final ThreadLocal<Queue<Integer>> collection = new ThreadLocal<Queue<Integer>>() {
		
		@Override
		protected Queue<Integer> initialValue() {
			
			return new Queue<Integer>();
		}
	};
	
	/************************************************************************************************************
	 * A container for tallying the rolling summary of color counts for a specific thread. The size of the
	 * underlying list data structure is determined by the number of individual colors in the <CODE>Color<CODE>
	 * enum.
	 */
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
	
	/************************************************************************************************************
	 * A modifier method to set the size of the collection of data used by this class.
	 * 
	 * @param size
	 *        the size of the collection
	 * 
	 * @postcondition
	 *   The collection size has been stored and is available to this class during execution.
	 */
	public static void setCollectionSize(int size) {
		
		collectionSize = size;
	}
	
	/************************************************************************************************************
	 * A modifier method to collect and store a single red item.
	 * 
	 * @param item
	 *        the item to enqueue into a color-specific Queue
	 * 
	 * @postcondition
	 *   A single color-specific item has been added to this class's internal Queue.
	 */
	public static void enqueueRedTotal(Integer item) {
		
		totalsRed.enqueue(item);
	}
	
	/************************************************************************************************************
	 * A modifier method to collect and store a single brown item.
	 * 
	 * @param item
	 *        the item to enqueue into a color-specific Queue
	 * 
	 * @postcondition
	 *   A single color-specific item has been added to this class's internal Queue.
	 */
	public static void enqueueBrownTotal(Integer item) {
		
		totalsBrown.enqueue(item);
	}
	
	/************************************************************************************************************
	 * A modifier method to collect and store a single yellow item.
	 * 
	 * @param item
	 *        the item to enqueue into a color-specific Queue
	 * 
	 * @postcondition
	 *   A single color-specific item has been added to this class's internal Queue.
	 */
	public static void enqueueYellowTotal(Integer item) {
		
		totalsYellow.enqueue(item);
	}
	
	/************************************************************************************************************
	 * A modifier method to collect and store a single green item.
	 * 
	 * @param item
	 *        the item to enqueue into a color-specific Queue
	 * 
	 * @postcondition
	 *   A single color-specific item has been added to this class's internal Queue.
	 */
	public static void enqueueGreenTotal(Integer item) {
		
		totalsGreen.enqueue(item);
	}
	
	/************************************************************************************************************
	 * A modifier method to collect and store a single blue item.
	 * 
	 * @param item
	 *        the item to enqueue into a color-specific Queue
	 * 
	 * @postcondition
	 *   A single color-specific item has been added to this class's internal Queue.
	 */
	public static void enqueueBlueTotal(Integer item) {
		
		totalsBlue.enqueue(item);
	}
	
	/************************************************************************************************************
	 * An accessor method to get a thread-specific collection.
	 * 
	 * @return
	 *   The Queue&ltInteger&gt collection, specific to the current thread.
	 */
	public static Queue<Integer> getLocalCollection() {
		
		return collection.get();
	}
	
	/************************************************************************************************************
	 * A modifier method to store a specific Integer item in an internal, thread-specific list.
	 * 
	 * @param indexItem
	 *        the item to add to an internal thread-specific summary list 
	 * <p>
	 * <b>Note:</b>
	 *   Only a single count of the specified item is incremented in the internal list.
	 * 
	 * @postcondition
	 *   A specific Integer item has been added to an internal, thread-specific list.
	 */
	public static void addToSummaryList(Integer indexItem) {
		
		int i = summaryList.get().get(indexItem);
		
		summaryList.get().set(indexItem, (i + 1));
	}
	
	/************************************************************************************************************
	 * Prints the results of the final data output for the collection set.
	 */
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
