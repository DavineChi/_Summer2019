package edu.metrostate.ICS440.assignment1;

/****************************************************************************************************************
 * A class to maintain a queue data structure over a generic collection of objects.
 * <p>
 *  
 * <b>Note:</b>
 *   Parts of this class implementation have been borrowed from Data Structures and Other Objects Using Java,
 *   4th edition, written by Michael Main.
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.05.23
 * <p>
 * Due Date:	2019.06.06
 */
public class Queue<T> {

	private Item<T> front;
	private Item<T> rear;
	private int size;
	
	/************************************************************************************************************
	 * Constructor used to create a new Queue object.
	 * <p>
	 * 
	 * @postcondition
	 *   A new Queue object has been created.
	 */
	public Queue() {
		
		this.front = null;
		this.rear = null;
	}
	
	/************************************************************************************************************
	 * A modifier method to add a new element to the back of this Queue.
	 * <p>
	 * 
	 * @param item
	 *        the item to add to this Queue
	 * 
	 * @postcondition
	 *   A new element has been added to the back of this Queue and the Queue size is incremented by one.
	 */
	public void enqueue(T item) {
		
		if (isEmpty()) {
			
			front = new Item<T>(item, null);
			rear = front;
		}
		
		else {
			
			rear.addAfter(item);
			
			rear = rear.getLink();
		}
		
		size++;
	}
	
	/************************************************************************************************************
	 * A modifier method to remove the next element in this Queue.
	 * <p>
	 * 
	 * @precondition
	 *   This Queue is not empty.
	 * 
	 * @postcondition
	 *   The next element in the Queue has been removed and the Queue size is decremented by one.
	 * 
	 * @return
	 *   The next element in this Queue.
	 */
	public T dequeue() {
		
		T result = null;
		
		synchronized (this) {
			
			if (size > 0) {
				
				result = front.getData();
				front = front.getLink();
				
				size--;
			}
		}
		
		return result;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the size of this Queue.
	 * <p>
	 * 
	 * @return
	 *   The size of this Queue.
	 */
	public int size() {
		
		return size;
	}
	
	/************************************************************************************************************
	 * An accessor method to check if this Queue is empty.
	 * <p>
	 * 
	 * @return
	 *   True if this Queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		
		return (size == 0);
	}
}