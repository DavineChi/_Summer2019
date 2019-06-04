// Modulus for keeping track of cursor index:
// https://en.wikipedia.org/wiki/Circular_buffer

package edu.metrostate.ICS462.assignment3;

public class Buffer {

	private int[] data;
	public int capacity; // How many can be stored.
	private int size;     // How many are currently stored.
	private int cursor;
	
	public Buffer(int initialCapacity) {
		
		this.data = new int[initialCapacity];
		this.capacity = initialCapacity;
		this.size = 0;
		this.cursor = 0;
		
		initialize();
	}
	
	private void initialize() {
		
		for (int i = 0; i < capacity; i++) {
			
			data[i] = -1;
		}
	}

	public void add(int item) {
		
		data[cursor] = item;
		
		cursor = Math.floorMod((size + 1), data.length);
		
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
	
	public boolean isEmpty() {
		
		return capacity == 0;
	}
	
	public boolean isFull() {
		
		return size == capacity;
	}
	
	public static void main(String[] args) {
		
		Buffer buffer = new Buffer(5);
		
		// TODO: Will need to think of how to invert the indexing for the poll()
		//       method, so that the last item added isn't the next item retrieved.
		
		buffer.add(2);
		buffer.add(3);
		buffer.add(4);
		buffer.add(5);
		buffer.add(6);
		buffer.add(10);
		buffer.add(11);
		buffer.add(12);
		buffer.add(15);
		buffer.add(99);
		
		for (int i = 0; !buffer.isEmpty(); i++) {
			
			int value;
			
			value = buffer.poll();
		}
	}
}
