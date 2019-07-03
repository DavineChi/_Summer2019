package edu.metrostate.ICS440.assignment3;

import java.util.Arrays;
import java.util.LinkedList;

/****************************************************************************************************************
 * This runnable routes Packets as they traverse the network.
 * <p>
 * 
 */
public class Router implements Runnable {
	
	private LinkedList<Packet> workQueue = new LinkedList<Packet>();  // TODO: Only need to lock access to this.
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
		workQueue.add(packet);
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// TODO: implementation
		// this method is only called one time (may need to keep track of this somehow)
		return;
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
		// Only lock accesses to the queue.
		// Before causing a thread to sleep/wait, check the truth table's conditions first.
		// Need a "process", "return", "wait" loop structure with truth table checks.
		
		synchronized (this) {
			
			System.out.println("Inside synchronized: " + Thread.currentThread().getId());
			
			while (workQueue.isEmpty()) {
				
				System.out.println("Inside while-loop: " + Thread.currentThread().getId());
				
				try {
					
					System.out.println("Before calling wait(): " + Thread.currentThread().getId());
					this.wait();
				}
				
				catch (InterruptedException ex) {
					
					Thread.currentThread().interrupt();
					ex.printStackTrace();
				}
			}
		}
		
		for (Packet packet : workQueue) {
			
			int packetDestination = packet.getDestination();
			
			if (this.routerNum != packetDestination) {
				
				routers[packetDestination].addWork(packet);
				this.notifyAll();
			}
			
			else {
				
				packet.record(routerNum);
				this.notifyAll();
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "list=" + workQueue + ", routes=" + Arrays.toString(routes) + ", routers=" +
		       Arrays.toString(routers) + ", routerNum=" + routerNum + ", end=" + end;
	}
}