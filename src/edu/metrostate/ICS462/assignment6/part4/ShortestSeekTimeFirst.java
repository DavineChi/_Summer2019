package edu.metrostate.ICS462.assignment6.part4;

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
		// TODO: implementation
	}

	@Override
	public String getAlgorithmName() {
		return "Shortest-Seek-Time-First";
	}
}
