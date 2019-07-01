package edu.metrostate.ICS462;

import java.util.Vector;

public class Requests {

	private Vector<Integer> cylinders = new Vector<Integer>();
	
	public Requests() {
		
		
	}
	
	public synchronized void add(Integer cylinder) {
		
		// TODO: Implementation
		
		// After adding a cylinder, notify all waiting threads.
		notifyAll();
	}
	
	public synchronized Vector<Integer> get(boolean waitIfEmpty) {
		
		// TODO: Implementation
		
		while (waitIfEmpty) {}  // wait...
		
		return null;
	}
}
