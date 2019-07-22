package edu.metrostate.ICS462.assignment6.part3;

import java.util.Collections;

/**
 * Implements the Elevator algorithm.
 * 
 * @author Brahma Dathan
 *
 */
public class Look extends Scheduler {
	private int cursorPrevious;
	private int cursorUp;
	private int cursorDown;
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
		cursorPrevious = 0;
		cursorUp = 0;
		cursorDown = Analyzer.NUMBER_OF_CYLINDERS;
		direction = Direction.UP;
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
			if (item >= cursorUp) {
				cursorUp = item;
				if (tempRequests.remove(item)) {
					// update statistics
					int distanceMoved = cursorUp - tracksMoved;
					int sleepTime = sleep(distanceMoved);
					elapsedTime = elapsedTime + sleepTime;
					tracksMoved = cursorUp;
					processed++;
				} else {
					// If the item was not or could not be removed
					System.out.println("Element could not be removed from list. Exiting now.");
					System.exit(1);
				}
			} else {
				// if there is no such request change direction
				direction = Direction.DOWN;
			}
		}
		
		if (direction == Direction.DOWN) {
			// otherwise, process the largest request smaller than
			// or equal to the current head position
			Integer item = tempRequests.get(tempRequests.size() - 1);
			if (item <= cursorDown) {
				cursorDown = item;
				if (tempRequests.remove(item)) {
					// update statistics
					int distanceMoved = Math.abs(cursorDown - cursorPrevious);
					tracksMoved = tracksMoved + distanceMoved;
					int sleepTime = sleep(distanceMoved);
					elapsedTime = elapsedTime + sleepTime;
					cursorPrevious = cursorDown;
					processed++;
				} else {
					// If the item was not or could not be removed
					System.out.println("Element could not be removed from list. Exiting now.");
					System.exit(1);
				}
			}
		} else {
			// if there is no such request change direction
			direction = Direction.UP;
		}
	}

	@Override
	public String getAlgorithmName() {
		return "Look";
	}
}
