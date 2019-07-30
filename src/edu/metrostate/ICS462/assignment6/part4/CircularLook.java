package edu.metrostate.ICS462.assignment6.part4;

import java.util.Comparator;
import java.util.ListIterator;

/**
 * Implements the Circular Look algorithm.
 * 
 * @author Shannon Fisher
 *
 */
public class CircularLook extends Scheduler {
	
	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public CircularLook(Requests requests, int numberOfRequests) {
		super(requests, numberOfRequests);
	}

	@Override
	public void initialize() {
		currentPosition = 0;
	}

	@Override
	public void processNextRequest() {
		
		tempRequests.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer integer1, Integer integer2) {
				return integer1.compareTo(integer2);
			}
		});
		Integer lastRequest = tempRequests.get(tempRequests.size() - 1);
		if (lastRequest.compareTo(currentPosition) >= 0) {
			ListIterator<Integer> iterator = tempRequests.listIterator();
			boolean finished = false;
			while (!finished) {
				Integer nextRequest = iterator.next();
				if (nextRequest.compareTo(currentPosition) >= 0) {
					iterator.remove();
					processed++;
					int distance = nextRequest - currentPosition;
					tracksMoved = tracksMoved + distance;
					elapsedTime = elapsedTime + sleep(Math.abs(distance));
					currentPosition = nextRequest;
					finished = true;
				}
			}
		} else {
			Integer firstRequest = tempRequests.get(0);
			int distance = currentPosition - firstRequest;
			tracksMoved = tracksMoved + distance;
			elapsedTime = elapsedTime + sleep(Math.abs(distance));
			currentPosition = firstRequest;
		}
	}

	@Override
	public String getAlgorithmName() {
		return "Circular Look";
	}
}
