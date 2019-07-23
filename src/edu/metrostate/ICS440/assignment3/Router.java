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
	//private boolean packetsInQueue;
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
		
		synchronized (this) {
			
			packetQueue.add(packet);
			
			//packetsInQueue = true;
			packetsInNetwork = true;
			
			this.notifyAll();
		}
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		while (packetsInNetwork) {
			
			try {
				
				this.wait();
			}
			
			catch (InterruptedException ex) {
				
				ex.printStackTrace();
			}
		}
		
		end = true;
	}
	
	/************************************************************************************************************
	 * Indicates that the network is empty.
	 * <p>
	 * 
	 */
	public synchronized void networkEmpty() {
		
		if (Routing.getPacketCount() == 0) {
			
			packetsInNetwork = false;
			this.notifyAll();
		}
	}
	
	/************************************************************************************************************
	 * Process packets. Add some details on how this works.
	 * <p>
	 * 
	 */
	@Override
	public void run() {
		
		while (!end) {
			
			Packet packet = null;
			int packetDestination;
			
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
			
			synchronized (this) {
				
				packet = packetQueue.poll();
				
				this.notifyAll();
			}
			
			packetDestination = packet.getDestination();
			
			packet.record(routerNum);
			
			if (routerNum != packetDestination) {
				
				int route = routes[packetDestination];
				
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