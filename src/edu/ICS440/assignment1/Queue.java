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
			
			rear = new Item<T>(item, null);
			front = rear;
		}
		
		else {
			
			rear = new Item<T>(item, rear);
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
	
	public int size() {
		
		return size;
	}
	
	public boolean isEmpty() {
		
		return (size == 0);
	}
}