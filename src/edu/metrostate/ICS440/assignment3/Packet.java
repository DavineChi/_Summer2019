package edu.metrostate.ICS440.assignment3;

import java.util.LinkedList;

/****************************************************************************************************************
 * Contains a Packet that traverses the network.
 * <p>
 * 
 */
class Packet {
	
	// The final router
	private int destination;
	// The origin router
	private int source;
	// The path this packet takes
	private LinkedList<Integer> path = new LinkedList<Integer>();
	
	/************************************************************************************************************
	 * Instantiate a Packet, given source and destination.
	 * <p>
	 * 
	 */
	Packet(int s, int d) {
		
		source = s;
		destination = d;
	}

	/************************************************************************************************************
	 * Return the Packet's source.
	 * <p>
	 * 
	 */
	int getSource() {
		
		return source;
	}
	
	/************************************************************************************************************
	 * Return the Packet's destination.
	 * <p>
	 * 
	 */
	int getDestination() {
		
		return destination;
	}
	
	/************************************************************************************************************
	 * Record the current location as the Packet traverses the network.
	 * <p>
	 * 
	 */
	void record(int router) {
		
		path.add(router);
	}
	
	/************************************************************************************************************
	 * Print the route the Packet took through the network.
	 * <p>
	 * 
	 */
	void print() {
		
		System.out.println("Packet source=" + source + " destination=" + destination);
		System.out.print("    path: ");
		
		for (int i = 0; i < path.size(); i++) {
			
			System.out.print(" " + path.get(i));
		}
		
		System.out.println();
	}
}