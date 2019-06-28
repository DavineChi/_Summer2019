package edu.metrostate.ICS440.assignment3;

import java.util.LinkedList;

/****************************************************************************************************************
 * This runnable routes Packets as they traverse the network.
 * <p>
 * 
 */
class Router implements Runnable {
	
	private LinkedList<Packet> list = new LinkedList<Packet>();
	private int routes[];
	private Router routers[];
	private int routerNum;
	private boolean end = false;
	
	Router(int rts[], Router rtrs[], int num) {
		routes = rts;
		routers = rtrs;
		routerNum = num;
	}
	
	/************************************************************************************************************
	 * Add a packet to this router. Add some details on how this works.
	 * <p>
	 * 
	 */
	public void addWork(Packet p) {
		
		// TODO: implementation
	}
	
	/************************************************************************************************************
	 * End the thread, once no more packets are outstanding.
	 * <p>
	 * 
	 */
	public synchronized void end() {
		
		// TODO: implementation
	}
	
	public synchronized void networkEmpty() {}

	/************************************************************************************************************
	 * Process packets. Add some details on how this works.
	 * <p>
	 * 
	 */
	public void run() {
		
		// TODO: implementation
	}
}