/**
 * 
 */

package edu.ICS440.assignment1;

public class Queue<T> {

	private Item<T> front;
	private Item<T> rear;
	private int size;
	
	public Queue() {
		
		front = null;
		rear = null;
	}
	
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
	
	public T dequeue() {
		
		T result = null;
		
		if (size > 0) {
			
			result = front.getData();
			front = front.getLink();
			
			size--;
		}
		
		return result;
	}

	public T peek() {
		
		T result = null;
		
		if (size > 0) {
			
			result = front.getData();
		}
		
		return result;
	}
	
	public int size() {
		
		return size;
	}
	
	public boolean isEmpty() {
		
		return (size == 0);
	}
	
	public static void main(String[] args) {

		Queue<Integer> collection = new Queue<Integer>();
		
		for (int index = 0; index < 10000; index++) {
			
			int candidate = ((int)(Math.random() * 10000)) % 5;
			Integer integ = new Integer(candidate);
			
			collection.enqueue(integ);
		}
		
		for (int index = 0; index < 10000; index++) {
			
			String value = String.valueOf(collection.dequeue());
			System.out.println(value);
		}
	}
}