package edu.metrostate.ICS440.assignment3;

import java.util.Arrays;
import java.util.LinkedList;

/****************************************************************************************************************
 * This runnable routes Packets as they traverse the network.
 * <p>
 * 
 */
public class Router implements Runnable {
	
	private LinkedList<Packet> list = new LinkedList<Packet>();
	private int[] routes;
	private Router[] routers;
	private int routerNum;
	private boolean end = false;
	
	/************************************************************************************************************
	 * Constructor for a new Router object.
	 * <p>
	 * 
	 * @param routes
	 *   the routes that this Router is assigned to take
	 * 
	 * @param routers
	 *   a reference to the entire list of Routers in the network
	 * 
	 * @param routerNum
	 *   a unique Router ID
	 *   
	 */
	public Router(int[] routes, Router[] routers, int routerNum) {
		
		this.routes = routes;
		this.routers = routers;
		this.routerNum = routerNum;
	}
	
	/************************************************************************************************************
	 * Add a packet to this router. Add some details on how this works.
	 * <p>
	 * 
	 * @param packet
	 *   the Packet to add to this Router's work queue
	 */
	public void addWork(Packet packet) {
		
		// TODO: implementation
		synchronized (this) {
			
			list.add(packet);
		}
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// TODO: implementation
		try {
			
			Thread.currentThread().join();
		}
		
		catch (InterruptedException ex) {
			
			ex.printStackTrace();
		}
	}
	
	/************************************************************************************************************
	 * Indicates that the network is empty.
	 * <p>
	 * 
	 */
	public synchronized void networkEmpty() {
		
		// TODO: implementation
	}
	
	/************************************************************************************************************
	 * Process packets. Add some details on how this works.
	 * <p>
	 * 
	 */
	@Override
	public void run() {
		
		// TODO: implementation
		while (list.isEmpty()) {}
		
		for (Packet packet : list) {
			
			int packetDestination = packet.getDestination();
			
			if (this.routerNum != packetDestination) {
				
				// TODO: this is (probably) incorrect, but will suffice for now.
				// this.addWork(packet);
				// Intended behavior:
				// Forward the Packet to the ***next appropriate Router*** in the routing table.
				// Consider iterating over the routers array to find the "appropriate Router" for the Packet.
				
				for (int i = 0; i < routers.length; i++) {
					
					Router router = routers[i];
					
					int routerNumber = router.routerNum;
					
					if (routerNumber == packetDestination) {
						
						this.addWork(packet);
					}
				}
			}
			
			if (this.routerNum == packetDestination) {
				
				packet.record(routerNum);
			}
		}
	}

	@Override
	public String toString() {
		
		return "list=" + list + ", routes=" + Arrays.toString(routes) + ", routers=" +
		       Arrays.toString(routers) + ", routerNum=" + routerNum + ", end=" + end;
	}
}