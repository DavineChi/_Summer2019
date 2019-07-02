package edu.metrostate.ICS462.assignment6;

import java.util.Vector;

public class Requests {

	private Vector<Integer> cylinderList = new Vector<Integer>();
	
	public Requests() {
		
		
	}
	
	public synchronized void add(Integer cylinder) {
		
		// TODO: Implementation
		cylinderList.add(cylinder);
		// After adding a cylinder, notify all waiting threads.
		notifyAll();
	}
	
	public synchronized Vector<Integer> get(boolean waitIfEmpty) {
		
		// TODO: Implementation
		while (waitIfEmpty) {}  // wait...
		
		Vector<Integer> result = cylinderList;
		
		cylinderList.clear();
		
		return result;
	}
}
