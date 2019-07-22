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
		
		synchronized (this) {
			
			packetQueue.add(packet);
			this.notifyAll();
		}
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// TODO: this method is only called one time (may need to keep track of this)
		
		String threadName = Thread.currentThread().getName();
		
		boolean packetsInQueue = !packetQueue.isEmpty();
		boolean packetsInNetwork = !this.networkEmpty();
		
		while (packetsInQueue && packetsInNetwork) {
			
//			System.out.println("Inside end(), before wait: " + threadName);
//			System.out.println("  packetsInQueue=" + packetsInQueue);
//			System.out.println("packetsInNetwork=" + packetsInNetwork);
			
			try {
				
				this.wait();
			}
			
			catch (InterruptedException ex) {
				
				Thread.currentThread().interrupt();
				ex.printStackTrace();
			}
		}
		
//		System.out.println("Inside end(), after wait: " + threadName);
//		System.out.println("  packetsInQueue=" + packetsInQueue);
//		System.out.println("packetsInNetwork=" + packetsInNetwork);
		
		end = true;
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
		
		// Only lock accesses to the queue.
		// Need a "process", "return", "wait" loop structure with truth table checks.
		
		String threadName = Thread.currentThread().getName();
		
		while (!this.end) {
			
			synchronized (this) {
				
				while (packetQueue.isEmpty()) {
					
					try {
						
						this.wait();
					}
					
					catch (InterruptedException ex) {
						
						ex.printStackTrace();
					}
				}
			}
			
			Packet packet = null;
			int packetDestination;
			
			synchronized (this) {
				
				packet = packetQueue.poll();
				
				this.notifyAll();
			}
			
			packetDestination = packet.getDestination();
			
			packet.record(routerNum);
			
			if (this.routerNum != packetDestination) {
				
				int route = routes[packetDestination];
				
				//System.out.println("Sending packet with destination " + packet.getDestination() + " to Router " + allRouters[route].routerNum + ".");
				
				allRouters[route].addWork(packet);
			}
			
			else {
				
				String hashValue = String.valueOf(packet.hashCode());
				String destValue = String.valueOf(packet.getDestination());
				String destRouter = String.valueOf(routerNum);
				
				StringBuilder sbHashValue = new StringBuilder();
				StringBuilder sbDestValue = new StringBuilder();
				StringBuilder sbDestRouter = new StringBuilder();
				
				if (hashValue.length() == 6) {
					
					sbHashValue.append(hashValue + "    ");
				}
				
				else if (hashValue.length() == 7) {
					
					sbHashValue.append(hashValue + "   ");
				}
				
				else if (hashValue.length() == 8) {
					
					sbHashValue.append(hashValue + "  ");
				}
				
				else if (hashValue.length() == 9) {
					
					sbHashValue.append(hashValue + " ");
				}
				
				else if (hashValue.length() == 10) {
					
					sbHashValue.append(hashValue);
				}
				
				if (destValue.length() == 1) {
					
					sbDestValue.append(" " + destValue);
				}
				
				else if (destValue.length() == 2) {
					
					sbDestValue.append(destValue);
				}
				
				if (destRouter.length() == 1) {
					
					sbDestRouter.append(" " + destRouter);
				}
				
				else if (destRouter.length() == 2) {
					
					sbDestRouter.append(destRouter);
				}
				
				System.out.println("Packet " + sbHashValue.toString() +
						" with destination " + sbDestValue.toString() +
						" arrived at Router " + sbDestRouter.toString() +
						": " + packet.path);
				
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