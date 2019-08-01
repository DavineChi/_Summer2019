package edu.metrostate.ICS462.assignment6.part4;

import java.util.ListIterator;

/**
 * Implements the Shortest-Seek-Time-First algorithm.
 * 
 * @author Shannon Fisher
 *
 */
public class ShortestSeekTimeFirst extends Scheduler {
	
	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public ShortestSeekTimeFirst(Requests requests, int numberOfRequests) {
		super(requests, numberOfRequests);
	}

	@Override
	public void initialize() {
		currentPosition = 0;
	}

	@Override
	public void processNextRequest() {
		Integer smallest = Integer.MAX_VALUE;
		ListIterator<Integer> iterator = tempRequests.listIterator();
		Integer nextRequest = null;
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			int difference = Math.abs(next - currentPosition);
			if (difference <= smallest) {
				smallest = difference;
				nextRequest = next;
			}
		}
		tempRequests.remove(nextRequest);
		processed++;
		int distance = Math.abs(nextRequest - currentPosition);
		tracksMoved = tracksMoved + distance;
		elapsedTime = elapsedTime + sleep(distance);
		currentPosition = nextRequest;
	}

	@Override
	public String getAlgorithmName() {
		return "Shortest-Seek-Time-First";
	}
}
