package edu.metrostate.ICS440.assignment3;

import java.util.Arrays;
import java.util.LinkedList;

/****************************************************************************************************************
 * This runnable routes Packets as they traverse the network.
 * <p>
 * 
 */
public class Router implements Runnable {
	
	private LinkedList<Packet> packetQueue = new LinkedList<Packet>();
	private int[] routes;
	private Router[] allRouters;
	private int routerNum;
	private boolean end = false;
	private boolean packetsInNetwork;
	
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
		
		// Synchronized, since we need exclusive access to this queue.
		synchronized (this) {
			
			// We can add to the queue here, which will change the queue's state. This is
			// the appropriate place to do this since we are in a synchronized block.
			packetQueue.add(packet);
			
			// Set a flag to indicate that there are still some Packets in the network.
			packetsInNetwork = true;
			
			// The queue's state has changed, so notify all threads that may be waiting.
			this.notifyAll();
		}
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// As long as there are Packets in the network, wait for task execution to complete.
		while (packetsInNetwork) {
			
			try {
				
				this.wait();
			}
			
			catch (InterruptedException ex) {
				
				ex.printStackTrace();
			}
		}
		
		// A thread must have been notified to wake up, and there must no longer be any Packets
		// in the network. So set the end flag to true.
		end = true;
		
		// Some thread(s) may be waiting on this condition, so notify them of the state change.
		this.notifyAll();
	}
	
	/************************************************************************************************************
	 * Indicates that the network is empty.
	 * <p>
	 * 
	 */
	public synchronized void networkEmpty() {
		
		// Another process has signaled that the network is empty, so set the flag
		// to indicate that there are no longer any Packets in the network.
		packetsInNetwork = false;
		
		// Notify any threads that may be waiting on this flag.
		this.notifyAll();
		
	}
	
	/************************************************************************************************************
	 * Process packets. Add some details on how this works.
	 * <p>
	 * 
	 */
	@Override
	public void run() {
		
		// Begin looping, stop when the end boolean flag has been set.
		while (!end) {
			
			Packet packet = null;
			int packetDestination;
			
			// Synchronized, since we need exclusive access to this queue.
			synchronized (this) {
				
				// If the queue is empty, wait.
				while (packetQueue.isEmpty()) {
					
					try {
						
						this.wait();
					}
					
					catch (InterruptedException ex) {
						
						ex.printStackTrace();
					}
					
					// When a thread wakes up, it may see that the queue is still empty.
					// If it is empty, but the ending condition has been met, break out
					// of this inner while-loop.
					if (end) {
						
						break;
					}
				}
				
				// We can poll the queue here, which will change the queue's state. This is
				// the appropriate place to do this since we are in a synchronized block.
				packet = packetQueue.poll();
				
				// The queue's state has changed, so notify all threads that may be waiting.
				this.notifyAll();
			}
			
			// Check the state of the queue and the end condition. If the queue is empty
			// and the end flag is set to true, break out of the main while-loop.
			if (packetQueue.isEmpty() && end) {
				
				break;
			}
			
			// Get the Packet's destination value.
			packetDestination = packet.getDestination();
			
			// Record this Router in the Packet's path list.
			packet.record(routerNum);
			
			// If this Router is not the Packet destination, send it to the appropriate Router.
			if (routerNum != packetDestination) {
				
				// Look up the Router this Packet should be sent to using this Router's route list.
				int route = routes[packetDestination];
				
				// Send the Packet to the appropriate Router.
				allRouters[route].addWork(packet);
			}
			
			// Otherwise, this Router is the Packet destination. No further forwarding is
			// required. Let the network know this by decrementing the network's Packet count.
			else {
				
				Routing.decPacketCount();
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "list=" + packetQueue + ", routes=" + Arrays.toString(routes) + ", routers=" +
		       Arrays.toString(allRouters) + ", routerNum=" + routerNum + ", end=" + end;
	}
}