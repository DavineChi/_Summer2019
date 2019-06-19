package com.mockup;

import edu.metrostate.ICS440.assignment2.Queue;
import edu.metrostate.ICS440.assignment2.WeatherData;

// Queue sort algorithm can be found at end of this file.
// Reference:
// https://stackoverflow.com/questions/5134127/sorting-a-queue-using-same-queue#

/****************************************************************************************************************
 * This class provides convenience methods for Queue data structures.
 * <p>
 * 
 * <p>
 * Begin Date:	2019.06.18
 * <p>
 * Due Date:	2019.06.27
 */
public class QueueUtil {
	
	private static Queue<WeatherData> localQueue;
	
	// **********************************************************************************************************
	// Private helper method to determine the smallest item in the Queue.
	// 
//	private static int findMinimum(int sortingIndex) {
//		
//		int result = Integer.MAX_VALUE;
//		int size = localQueue.size();
//		
//		for (int i = 1; i <= size; i++) {
//			
//			int currentItem = localQueue.dequeue();
//			
//			if (currentItem <= result && i <= sortingIndex) {
//				
//				result = currentItem;
//			}
//			
//			localQueue.enqueue(currentItem);
//		}
//		
//		return result;
//	}
	
	// **********************************************************************************************************
	// Private helper method to determine the largest item in the Queue.
	// 
	private static WeatherData findMaximum(int sortingIndex) {
		
		WeatherData result = null;
		
		float largest = -9999.9f;
		
		int size = localQueue.size();
		
		for (int i = 1; i <= size; i++) {
			
			WeatherData item = localQueue.dequeue();
			
			float currentItemValue = item.getValue();
			
			if (currentItemValue > largest && i <= sortingIndex) {
				
				largest = currentItemValue;
				result = item;
			}
			
			localQueue.enqueue(item);
		}
		
		return result;
	}
	
	// **********************************************************************************************************
	// Private helper method to maintain ascending order on sorting.
	// 
//	private static void reorderAscending(int min) {
//		
//		int size = localQueue.size();
//		
//		for (int i = 1; i <= size; i++) {
//			
//			int currentItem = localQueue.dequeue();
//			
//			if (currentItem != min) {
//				
//				localQueue.enqueue(currentItem);
//			}
//		}
//		
//		localQueue.enqueue(min);
//	}
	
	// **********************************************************************************************************
	// Private helper method to maintain descending order on sorting.
	// 
	private static void reorderDescending(WeatherData max) {
		
		int counter = 0;
		
		int size = localQueue.size();
		
		for (int i = 1; i <= size; i++) {
			
			WeatherData item = localQueue.dequeue();
			
			//float currentItemValue = item.getValue();
			
			//if (currentItemValue != max) {
			if (!item.equals(max)) {
				
				localQueue.enqueue(item);
			}
			
			System.out.println("Inside reordering method, counter = " + counter);
			counter++;
		}
		
		localQueue.enqueue(max);
	}
	
	/************************************************************************************************************
	 * A modifier method to sort the specified Queue in ascending order.
	 * <p>
	 * 
	 * @param queue
	 *   the Queue to sort
	 * 
	 * @postcondition
	 *   The Queue has been sorted in ascending order.
	 */
//	public static void sortAscending(Queue<WeatherData> queue) {
//		
//		localQueue = queue;
//		
//		int size = localQueue.size();
//		
//		for (int i = 0; i <= (size - 1); i++) {
//			
//			int min = findMinimum(size - i);
//			
//			reorderAscending(min);
//		}
//		
//		queue = localQueue;
//		localQueue = null;
//	}
	
	/************************************************************************************************************
	 * A modifier method to sort the specified Queue in descending order.
	 * <p>
	 * 
	 * @param queue
	 *   the Queue to sort
	 * 
	 * @postcondition
	 *   The Queue has been sorted in descending order.
	 */
	public static void sortDescending(Queue<WeatherData> queue) {
		
		localQueue = queue;
		
		int size = localQueue.size();
		
		for (int i = 0; i <= (size - 1); i++) {
			
			//int max = findMaximum(size - i);
			WeatherData max = findMaximum(size - i);
			
			reorderDescending(max);
		}
		
		queue = localQueue;
		localQueue = null;
	}
	
	/************************************************************************************************************
	 * An acessor method to print the contents of the specified Queue.
	 * <p>
	 * 
	 * @param queue
	 *   the Queue to print
	 * 
	 * @postcondition
	 *   The specified Queue has been printed.
	 */
//	public static void print(Queue<WeatherData> queue) {
//		
//		int size = queue.size();
//		
//		for (int i = 0; i < size; i++) {
//			
//			int current = queue.dequeue();
//			
//			System.out.println(current);
//			
//			queue.enqueue(current);
//		}
//	}
}

/* **************************************************************************************************************
 *  Sort(Q,n):
 *  
 *    for i = 0 to n-1
 *      min = findMin(Q, n-i, n)
 *      reorder(Q, min, n)
 *    end for
 *  
 *  findMin(Q, k, n):
 *  
 *    min = infinity
 *  
 *    for i = 1 to n
 *      curr = dequeue(Q)
 *      if curr < min && i<=k
 *        min = curr
 *      end if
 *      enqueue(Q,curr)
 *    end for
 *  
 *    return min
 *  
 *  reorder(Q, min, n):
 *  
 *    for i = 1 to n
 *      curr = dequeue(Q)
 *      if (curr != min)
 *        enqueue(Q, curr)
 *      end if
 *    end for
 *  
 *    enqueue(Q,min)
 ****************************************************************************************************************/