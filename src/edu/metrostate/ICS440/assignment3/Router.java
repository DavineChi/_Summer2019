package edu.metrostate.ICS440.assignment3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/****************************************************************************************************************
 * This runnable routes Packets as they traverse the network.
 * <p>
 * 
 */
public class Router implements Runnable {
	
	private LinkedList<Packet> packetQueue = new LinkedList<Packet>();  // TODO: Only need to lock access to this.
	private int[] routes;
	private Router[] allRouters;
	private int routerNum;
	private boolean end = false;
	
	/************************************************************************************************************
	 * Constructor for a new Router object.
	 * <p>
	 * 
	 * @param routes
	 *   the routes that this Router is assigned to take
	 * 
	 * @param allRouters
	 *   a reference to the entire list of Routers in the network
	 * 
	 * @param routerNum
	 *   a unique Router ID
	 */
	public Router(int[] routes, Router[] allRouters, int routerNum) {
		
		this.routes = routes;
		this.allRouters = allRouters;
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
		
		synchronized (packetQueue) {
			
			if (threadId != 1) {
				
				System.out.println("Inside addWork(), inside synchronized: " + "Thread-" + threadId);
			}
			
			packetQueue.add(packet);
		}
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// TODO: implementation - method is only called one time (may need to keep track of this somehow)
		
		boolean noPacketsInQueue = packetQueue.isEmpty();
		boolean noPacketsInNetwork = this.networkEmpty();
		
		boolean packetsInQueue = !packetQueue.isEmpty();
		boolean packetsInNetwork = !this.networkEmpty();
		
		while (!packetsInQueue && !packetsInNetwork) {
			
			try {
				
				System.out.println("Inside end(), before wait: " + "Thread-" + Thread.currentThread().getId());
				this.wait();
			}
			
			catch (InterruptedException ex) {
				
				Thread.currentThread().interrupt();
				ex.printStackTrace();
			}
		}
		
		this.end = true;
		
		System.out.println("Inside end(), before notifyAll, end == " + this.end + ", " +
		                   "Thread-" + Thread.currentThread().getId());
		this.notifyAll();
	}
	
	/************************************************************************************************************
	 * Indicates that the network is empty.
	 * <p>
	 * 
	 */
	public synchronized boolean networkEmpty() {
		
		return Routing.getPacketCount() == 0;
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
		
		synchronized (packetQueue) {
			
			boolean noPacketsInQueue = packetQueue.isEmpty();
			boolean noPacketsInNetwork = this.networkEmpty();
			boolean endCalled = this.end;
			
			while (!(noPacketsInQueue && (!noPacketsInNetwork || !endCalled))) {
				
				try {
					
					packetQueue.wait();
				}
				
				catch (InterruptedException ex) {
					
					ex.printStackTrace();
				}
			}
			
			packetQueue.notifyAll();
		}
		
		while (!this.networkEmpty() && !packetQueue.isEmpty()) {
			
			// TODO: I think you will need to dequeue() at some point here...
			
			int iterations = packetQueue.size();
			
			for (int k = 0; k < iterations; k++) {
				
				Packet packet = packetQueue.poll();
				
				int packetDestination = packet.getDestination();
				
				packet.record(routerNum);
				
				if (this.routerNum != packetDestination) {
					
					allRouters[packetDestination].addWork(packet);
				}
				
				else {
					
					Routing.decPacketCount();
				}
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "list=" + packetQueue + ", routes=" + Arrays.toString(routes) + ", routers=" +
		       Arrays.toString(allRouters) + ", routerNum=" + routerNum + ", end=" + end;
	}
}