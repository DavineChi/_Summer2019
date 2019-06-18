package edu.metrostate.ICS440.assignment2;

// Algorithm used from the following:
// https://stackoverflow.com/questions/5134127/sorting-a-queue-using-same-queue#

/* *******************************************************************************
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
 *********************************************************************************/
public class QueueSorter {

	private static Queue<Integer> queue;
	
	private static void initQueue() {
		
		queue = new Queue<Integer>();
	}
	
	private static void fillQueue() {
		
		queue.enqueue(new Integer(5));
		queue.enqueue(new Integer(1));
		queue.enqueue(new Integer(10));
		queue.enqueue(new Integer(2));
		queue.enqueue(new Integer(7));
	}
	
	private static int findMinimum(int sortingIndex) {
		
		int result = Integer.MAX_VALUE;
		int size = queue.size();
		
		for (int i = 1; i <= size; i++) {
			
			int currentItem = queue.dequeue();
			
			if (currentItem <= result && i <= sortingIndex) {
				
				result = currentItem;
			}
			
			queue.enqueue(currentItem);
		}
		
		return result;
	}
	
	private static void reorder(int min) {
		
		int size = queue.size();
		
		for (int i = 1; i <= size; i++) {
			
			int currentItem = queue.dequeue();
			
			if (currentItem != min) {
				
				queue.enqueue(currentItem);
			}
		}
		
		queue.enqueue(min);
	}
	
	public static void sortMinToMax() {
		
		int size = queue.size();
		
		for (int i = 0; i <= (size - 1); i++) {
			
			int min = QueueSorter.findMinimum(size - i);
			
			QueueSorter.reorder(min);
		}
	}
	
	public static void main(String[] args) {
		
		QueueSorter.initQueue();
		QueueSorter.fillQueue();
		QueueSorter.sortMinToMax();
		
		String temp = "";
	}
}
