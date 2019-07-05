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
		
		long threadId = Thread.currentThread().getId();
		
		synchronized (workQueue) {
			
			System.out.println("Inside addWork(), inside synchronized: " + threadId);
			workQueue.add(packet);
		}
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// TODO: implementation
		// this method is only called one time (may need to keep track of this somehow)
		
		while (!networkEmpty()) {
			
			try {
				
				System.out.println("Inside end(), before wait: " + Thread.currentThread().getId());
				this.wait();
			}
			
			catch (InterruptedException ex) {
				
				Thread.currentThread().interrupt();
				ex.printStackTrace();
			}
		}
		
		this.end = true;
		
		System.out.println("Inside end(), before notifyAll: " + Thread.currentThread().getId());
		this.notifyAll();
	}
	
	/************************************************************************************************************
	 * Indicates that the network is empty.
	 * <p>
	 * 
	 */
	public synchronized boolean networkEmpty() {
		
		// TODO: implementation
		boolean result = true;
		
		for (int i = 0; i < routers.length; i++) {
			
			Router router = routers[i];
			
			if (!router.workQueue.isEmpty()) {
				
				result = false;
				break;
			}
		}
		
		return result;
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
		// Need a "process", "return", "wait" loop structure with truth table checks.
		
		synchronized (this) {
			
			System.out.println("Inside run(), inside synchronized: " + Thread.currentThread().getId());
			
			while (!workQueue.isEmpty()) {
				
				System.out.println("Inside run(), inside while-loop: " + Thread.currentThread().getId());
				
				try {
					
					// TODO: Before causing a thread to sleep/wait, check the truth table's conditions first.
					System.out.println("Inside run(), before wait: " + Thread.currentThread().getId());
					workQueue.wait();
				}
				
				catch (InterruptedException ex) {
					
					Thread.currentThread().interrupt();
					ex.printStackTrace();
				}
			}
			
			this.notifyAll();
		}
		
		for (Packet packet : workQueue) {
			
			int packetDestination = packet.getDestination();
			
			if (this.routerNum != packetDestination) {
				
				System.out.println("Inside packet loop, before addWork(): " + Thread.currentThread().getId());
				routers[packetDestination].addWork(packet);
			}
			
			else {
				
				packet.record(routerNum);
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "list=" + workQueue + ", routes=" + Arrays.toString(routes) + ", routers=" +
		       Arrays.toString(routers) + ", routerNum=" + routerNum + ", end=" + end;
	}
}