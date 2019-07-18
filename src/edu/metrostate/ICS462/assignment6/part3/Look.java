package edu.metrostate.ICS462.assignment6.part3;

import java.util.Collections;
import java.util.Vector;

/**
 * Implements the Elevator algorithm.
 * 
 * @author Brahma Dathan
 *
 */
public class Look extends Scheduler {
	private Direction direction;

	private enum Direction {
		UP, DOWN
	};

	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public Look(Requests requests, int numberOfRequests) {
		super(requests, numberOfRequests);
	}

	public void initialize() {
		// TODO:
	}

	@Override
	public void processNextRequest() {
		// rough pseudo code
		
		// sort the requests in ascending order of cylinder numbers
		
		Vector<Integer> list = requests.get(true);
		Collections.sort(list);
		
		
		
		
		String stop = "";
		// if direction is up
		// process the smallest request greater than or
		// equal to the current head position
		// update statistics
		// if there is no such request change direction
		// otherwise,
		// process the largest request smaller than or
		// equal to the current head position
		// update statistics
		// if there is no such request change direction
	}

	@Override
	public String getAlgorithmName() {
		return "Look";
	}
}
