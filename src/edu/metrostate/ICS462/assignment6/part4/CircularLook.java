package edu.metrostate.ICS462.assignment6.part4;

/**
 * Implements the Circular Look algorithm.
 * 
 * @author Shannon Fisher
 *
 */
public class CircularLook extends Scheduler {
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
	public CircularLook(Requests requests, int numberOfRequests) {
		super(requests, numberOfRequests);
	}

	@Override
	public void initialize() {
		currentPosition = 0;
		direction = Direction.UP;
	}

	@Override
	public void processNextRequest() {
		// TODO: implementation
	}

	@Override
	public String getAlgorithmName() {
		return "Circular Look";
	}
}
