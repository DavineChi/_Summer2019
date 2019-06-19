package edu.metrostate.ICS440.assignment2;

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
	
	private static Queue<Integer> localQueue;
	
	// **********************************************************************************************************
	// Private helper method to determine the smallest item in the Queue.
	// 
	private static int findMinimum(int sortingIndex) {
		
		int result = Integer.MAX_VALUE;
		int size = localQueue.size();
		
		for (int i = 1; i <= size; i++) {
			
			int currentItem = localQueue.dequeue();
			
			if (currentItem <= result && i <= sortingIndex) {
				
				result = currentItem;
			}
			
			localQueue.enqueue(currentItem);
		}
		
		return result;
	}
	
	// **********************************************************************************************************
	// Private helper method to determine the largest item in the Queue.
	// TODO: implementation and testing
	private static int findMaximum(int sortingIndex) {
		
		int result = Integer.MIN_VALUE;
		int size = localQueue.size();
		
		for (int i = 1; i <= size; i++) {
			
			int currentItem = localQueue.dequeue();
			
			if (currentItem > result && i <= sortingIndex) {
				
				result = currentItem;
			}
			
			localQueue.enqueue(currentItem);
		}
		
		return result;
	}
	
	// **********************************************************************************************************
	// Private helper method to maintain ascending order on sorting.
	// 
	private static void reorderAscending(int min) {
		
		int size = localQueue.size();
		
		for (int i = 1; i <= size; i++) {
			
			int currentItem = localQueue.dequeue();
			
			if (currentItem != min) {
				
				localQueue.enqueue(currentItem);
			}
		}
		
		localQueue.enqueue(min);
	}
	
	// **********************************************************************************************************
	// Private helper method to maintain descending order on sorting.
	// TODO: implementation and testing
	private static void reorderDescending(int max) {
		
		int size = localQueue.size();
		
		for (int i = 1; i <= size; i++) {
			
			int currentItem = localQueue.dequeue();
			
			if (currentItem != max) {
				
				localQueue.enqueue(currentItem);
			}
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
	public static void sortAscending(Queue<Integer> queue) {
		
		localQueue = queue;
		
		int size = localQueue.size();
		
		for (int i = 0; i <= (size - 1); i++) {
			
			int min = findMinimum(size - i);
			
			reorderAscending(min);
		}
		
		queue = localQueue;
		localQueue = null;
	}
	
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
	public static void sortDescending(Queue<Integer> queue) {
		
		// TODO: implementation and testing
		
		localQueue = queue;
		
		int size = localQueue.size();
		
		for (int i = 0; i <= (size - 1); i++) {
			
			int max = findMaximum(size - i);
			
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
	public static void print(Queue<Integer> queue) {
		
		int size = queue.size();
		
		for (int i = 0; i < size; i++) {
			
			int current = queue.dequeue();
			
			System.out.println(current);
			
			queue.enqueue(current);
		}
	}
	
	public static void main(String[] args) {
		
		Queue<Integer> testQueue = new Queue<Integer>();
		
		testQueue.enqueue(new Integer(5));
		testQueue.enqueue(new Integer(1));
		testQueue.enqueue(new Integer(9));
		testQueue.enqueue(new Integer(2));
		testQueue.enqueue(new Integer(7));
		
		QueueUtil.print(testQueue);
		QueueUtil.sortAscending(testQueue);
		QueueUtil.print(testQueue);
		QueueUtil.sortDescending(testQueue);
		QueueUtil.print(testQueue);
		
		String temp = "";
	}
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