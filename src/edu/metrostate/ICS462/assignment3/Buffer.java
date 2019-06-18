// Modulus for keeping track of cursor index:
// https://en.wikipedia.org/wiki/Circular_buffer

package edu.metrostate.ICS462.assignment3;

public class Buffer {
	
	private int[] data;
	private int capacity;  // How many can be stored.
	private int size;      // How many are currently stored.
	private int cursor;
	
	/************************************************************************************************************
	 * Constructor used to create a new circular Buffer object with a specified capacity.
	 * <p>
	 * 
	 * @param capacity
	 *   the specified holding capacity for this Buffer
	 * 
	 * @postcondition
	 *   A new circular Buffer object has been created with a specified capacity.
	 */
	public Buffer(int capacity) {
		
		this.data = new int[capacity];
		this.capacity = capacity;
		this.size = 0;
		this.cursor = 0;
		
		initialize();
	}
	
	private void initialize() {
		
		for (int i = 0; i < capacity; i++) {
			
			data[i] = -1;
		}
	}
	
	public int add(int item) {
		
		int result = cursor;
		
		data[cursor] = item;
		
		cursor = Math.floorMod((size + 1), data.length);
		
		size++;
		
		return result;
	}
	
//	public void add(int item) {
//		
//		data[cursor] = item;
//		
//		cursor = Math.floorMod((size + 1), data.length);
//		
//		size++;
//	}
	
	public void add(int item, int index) {
		
		data[index] = item;
		
		cursor = Math.floorMod((index + 1), data.length);
		
		size++;
	}
	
	public int poll() {
		
		int result;
		
		cursor = Math.floorMod((size + data.length - 1), data.length);
		
		result = data[cursor];
		
		data[cursor] = -1;
		
		size--;
		
		return result;
	}
	
	public int[] remove(int index) {
		
		int[] result = new int[2];
		
		result[0] = data[index];
		result[1] = index;
		
		data[index] = -1;
		
		size--;
		
		return result;
	}
	
	public int peek(int index) {
		
		return data[index];
	}
	
	public int getElement(int index) {
		
		int result = data[index];
		
		data[index] = -1;
		
		size--;
		
		return result;
	}
	
	public int getSize() {
		
		return size;
	}
	
	public int getCapacity() {
		
		return capacity;
	}
	
	public boolean isEmpty() {
		
		return capacity == 0;
	}
	
	public boolean isFull() {
		
		boolean full = true;
		
		for (int i = 0; i < data.length; i++) {
			
			if (i == -1) {
				
				full = false;
				
				break;
			}
		}
		
		return size == capacity && full;
	}
	
//	public static void main(String[] args) {
//		
//		Buffer buffer = new Buffer(5);
//		
//		buffer.add(2);
//		buffer.add(3);
//		buffer.add(4);
//		buffer.add(5);
//		buffer.add(6);
//		buffer.add(10);
//		buffer.add(11);
//		buffer.add(12);
//		buffer.add(15);
//		buffer.add(99);
//		
//		for (int i = 0; !buffer.isEmpty(); i++) {
//			
//			int value;
//			
//			value = buffer.poll();
//		}
//	}
}
