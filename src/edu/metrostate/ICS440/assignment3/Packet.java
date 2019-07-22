package edu.metrostate.ICS440.assignment3;

import java.util.LinkedList;

/****************************************************************************************************************
 * Contains a Packet that traverses the network.
 * <p>
 * 
 */
public class Packet {
	
	// The origin router.
	private int source;
	
	// The final router.
	private int destination;
	
	// The path this packet takes.
	public LinkedList<Integer> path = new LinkedList<Integer>();  // TODO: change back to private when done
	
	/************************************************************************************************************
	 * Constructor for a new Packet object.
	 * <p>
	 * 
	 * @param source
	 *   the source of this Packet's route
	 * 
	 * @param destination
	 *   the destination of this Packet's route
	 */
	public Packet(int source, int destination) {
		
		this.source = source;
		this.destination = destination;
	}

	/************************************************************************************************************
	 * Return the Packet's source.
	 * <p>
	 * 
	 */
	public int getSource() {
		
		return source;
	}
	
	/************************************************************************************************************
	 * Return the Packet's destination.
	 * <p>
	 * 
	 */
	public int getDestination() {
		
		return destination;
	}
	
	/************************************************************************************************************
	 * Record the current location as the Packet traverses the network.
	 * <p>
	 * 
	 */
	public void record(int router) {
		
		path.add(router);
	}
	
	/************************************************************************************************************
	 * Print the route the Packet took through the network.
	 * <p>
	 * 
	 */
	public void print() {
		
		System.out.println("Packet source=" + source + " destination=" + destination);
		System.out.print("    path: ");
		
		for (int i = 0; i < path.size(); i++) {
			
			System.out.print(" " + path.get(i));
		}
		
		System.out.println();
	}
	
	@Override
	public String toString() {
		
		return "source=" + source + ", destination=" + destination + ", path=" + path;
	}
}