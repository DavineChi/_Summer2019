package edu.metrostate.ICS462.assignment6.part3;

import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Implements the Elevator algorithm.
 * 
 * @author Brahma Dathan
 *
 */
public class Look extends Scheduler {
	private Direction direction;
	private int cursor;

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
		direction = Direction.UP;
		cursor = 0;
	}

	@Override
	public void processNextRequest() {
		// sort the requests in ascending order of cylinder numbers
		Collections.sort(tempRequests);
		// if direction is up
		if (direction == Direction.UP) {
			// process the smallest request greater than or
			// equal to the current head position
			Integer item = tempRequests.get(0);
			if (item >= cursor) {
				cursor = item;
				if (tempRequests.remove(item)) {
					// update statistics
					int distance = cursor - tracksMoved;
					int sleepyPiss = sleep(distance);
					elapsedTime = elapsedTime + sleepyPiss;
					tracksMoved = cursor;
					processed++;
				} else {
					try {
						throw new NoSuchElementException();
					} catch (NoSuchElementException ex) {
						ex.printStackTrace();
					}
				}
			} else {
				// if there is no such request change direction
				direction = Direction.DOWN;
			}
		} else {
			// otherwise,
			// process the largest request smaller than or
			// equal to the current head position
			// update statistics
			// if there is no such request change direction
			direction = Direction.UP;
		}
	}

	@Override
	public String getAlgorithmName() {
		return "Look";
	}
}
