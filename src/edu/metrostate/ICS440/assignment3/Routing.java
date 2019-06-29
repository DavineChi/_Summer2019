package edu.metrostate.ICS440.assignment3;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/****************************************************************************************************************
 * Simulate a network of routers, with each router as a thread, passing packets from source to destination.
 * <p>
 * 
 */
public class Routing {
	
	/************************************************************************************************************
	 * This is the routing table. It implements the function f(r, c), where r corresponds to the row, and c
	 * corresponds to the column. r is the current router, and c is the destination of the packet.  f(r, c)
	 * gives the next router for the shortest path route.
	 * <p>
	 * 
	 */
	private static int routingTable[][] = {
			
			// 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31
			{ -1, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13 }, // 0
			{  2, -1,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2, 27,  2,  2,  2,  2 }, // 1
			{ 18,  1, -1, 18, 18, 18, 18, 18, 18,  9, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18,  1, 18, 18, 18, 18 }, // 2
			{ 10, 10, 10, -1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 }, // 3
			{ 24, 24, 24, 24, -1, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24 }, // 4
			{ 12, 10, 10, 10, 12, -1, 12, 10, 10, 10, 10, 10, 12, 12, 12, 12, 10, 10, 10, 12, 12, 10, 10, 12, 12, 12, 12, 10, 12, 10, 10, 10 }, // 5
			{ 15, 15, 15, 15, 15, 15, -1, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 19, 20, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15 }, // 6
			{ 22, 22, 22, 22, 22, 22, 22, -1, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22 }, // 7
			{ 21, 21, 21, 21, 21, 21, 21, 21, -1, 21, 21, 21, 21, 21, 21, 21, 21, 17, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 17, 21 }, // 8
			{  2,  2,  2,  2,  2,  2,  2,  2,  2, -1,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2 }, // 9
			{ 18, 18, 18,  3, 18,  5, 18, 21, 21, 18, -1, 18,  5, 18, 18, 18, 21, 21, 18, 18, 18, 21, 21, 18, 18, 18, 18, 18, 18, 21, 21, 31 }, //10
			{ 23, 18, 18, 18, 23, 18, 23, 18, 18, 18, 18, -1, 23, 23, 23, 23, 18, 18, 18, 23, 23, 18, 18, 23, 23, 23, 23, 18, 23, 18, 18, 18 }, //11
			{ 23,  5,  5,  5, 23,  5, 23,  5,  5,  5,  5, 23, -1, 23, 23, 23,  5,  5,  5, 23, 23,  5,  5, 23, 23, 23, 23,  5, 23,  5,  5,  5 }, //12
			{  0, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, -1, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23 }, //13
			{ 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, -1, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23 }, //14
			{ 25, 25, 25, 25, 25, 25,  6, 25, 25, 25, 25, 25, 25, 25, 25, -1, 25, 25, 25,  6,  6, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25 }, //15
			{ 21, 21, 21, 21, 21, 21, 21, 22, 21, 21, 21, 21, 21, 21, 21, 21, -1, 21, 21, 21, 21, 21, 22, 21, 21, 21, 21, 21, 21, 29, 21, 21 }, //16
			{  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8, -1,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8, 30,  8 }, //17
			{ 11,  2,  2, 10, 11, 10, 11, 10, 10,  2, 10, 11, 10, 11, 11, 11, 10, 10, -1, 11, 11, 10, 10, 11, 11, 11, 11,  2, 11, 10, 10, 10 }, //18
			{  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6, -1,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6 }, //19
			{  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6, -1,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6,  6 }, //20
			{ 10, 10, 10, 10, 10, 10, 10, 16,  8, 10, 10, 10, 10, 10, 10, 10, 16,  8, 10, 10, 10, -1, 16, 10, 10, 10, 10, 10, 10, 16,  8, 10 }, //21
			{ 16, 16, 16, 16, 16, 16, 16,  7, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16, 16, 16, 16, 16, 16, 16, 16 }, //22
			{ 13, 11, 11, 12, 28, 12, 28, 12, 12, 11, 12, 11, 12, 13, 14, 28, 12, 12, 11, 28, 28, 12, 12, -1, 28, 28, 28, 11, 28, 12, 12, 12 }, //23
			{ 26, 26, 26, 26,  4, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, -1, 26, 26, 26, 26, 26, 26, 26 }, //24
			{ 26, 26, 26, 26, 26, 26, 15, 26, 26, 26, 26, 26, 26, 26, 26, 15, 26, 26, 26, 15, 15, 26, 26, 26, 26, -1, 26, 26, 26, 26, 26, 26 }, //25
			{ 28, 28, 28, 28, 24, 28, 25, 28, 28, 28, 28, 28, 28, 28, 28, 25, 28, 28, 28, 25, 25, 28, 28, 28, 24, 25, -1, 28, 28, 28, 28, 28 }, //26
			{  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1,  1 }, //27
			{ 23, 23, 23, 23, 26, 23, 26, 23, 23, 23, 23, 23, 23, 23, 23, 26, 23, 23, 23, 26, 26, 23, 23, 23, 26, 26, 26, 23, -1, 23, 23, 23 }, //28
			{ 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16 }, //29
			{ 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, 17 }, //30
			{ 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, -1 }, //31
	};
	
	public static AtomicInteger packetCount = new AtomicInteger(0);
	
	// A table of routers, one for each vertex in the network.
	public static Router[] router = new Router[routingTable.length];
	
	public static int getPacketCount() {

		return packetCount.get();
	}
	
	public static void incPacketCount() {

		packetCount.getAndIncrement();
	}
	
	/************************************************************************************************************
	 * Decrement the packet count, and if there are no packets left, let the network know.
	 * <p>
	 * 
	 */
	public static void decPacketCount() {
		
		if ((packetCount.decrementAndGet()) == 0) {
			
			for (int i = 0; i < routingTable.length; i++) {
				
				router[i].networkEmpty();
			}
		}
	}
	
	/************************************************************************************************************
	 * Run the program.
	 * <p>
	 * 
	 */
	public static void main(String args[]) {

		// A table of threads, one for each thread in the network.
		Thread[] thread = new Thread[routingTable.length];
		LinkedList<Packet> packetList = new LinkedList<Packet>();

		// A reference to a Packet, that will be introduced to the network.
		Packet packet;

		// A random number generator, for generating random Packets.
		Random random = new Random();

		// ******************************************************************************************************
		// Create a Router and corresponding thread, and start the thread.
		//
		for (int i = 0; i < routingTable.length; i++) {
			
			router[i] = new Router(routingTable[i], router, i);
			thread[i] = new Thread(router[i]);
			
			thread[i].start();
		}

		// ******************************************************************************************************
		// Create some Packets, and insert them at the source Router.
		//
		for (int i = 0; i < 500; i++) {
			
			int source = random.nextInt(routingTable.length);
			int destination = random.nextInt(routingTable.length);
			
			packet = new Packet(source, destination);
			
			incPacketCount();
			packetList.add(packet);
			router[packet.getSource()].addWork(packet);
		}
		
		// ******************************************************************************************************
		// Let the network quiesce.
		//
		try {
			
			Thread.sleep(500);
		}

		catch (InterruptedException ex) {
			
			
		}

		finally {
			
			
		}
		
		// ******************************************************************************************************
		// Create some Packets, and insert them at the source Router. The network should start processing again.
		//
		for (int i = 0; i < 500; i++) {
			
			int source = random.nextInt(routingTable.length);
			int destination = random.nextInt(routingTable.length);
			
			packet = new Packet(source, destination);
			
			incPacketCount();
			packetList.add(packet);
			router[packet.getSource()].addWork(packet);
		}

		/* MOVE TO FIX A BUG */

		/* for (int i = 0; i < packetList.size(); i++) {
            packetList.get(i).Print();
        } */
		
		
		// ******************************************************************************************************
		// Shut down each thread gracefully.
		//
		for (int i = 0; i < routingTable.length; i++) {
			
			router[i].end();
			
			try {
				
				thread[i].join();
			}
			
			catch (InterruptedException ex) {
				
				
			}
			
			finally {
				
				
			}
		}
		
		// ******************************************************************************************************
		// Print the route of all Packets that traversed the network.
		//
		for (int i = 0; i < packetList.size(); i++) {

			packetList.get(i).print();
		}
	}
}