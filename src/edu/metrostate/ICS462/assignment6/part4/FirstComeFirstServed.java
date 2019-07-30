package edu.metrostate.ICS462.assignment6.part4;

/**
 * Implements the First-Come First-Served algorithm.
 * 
 * @author Shannon Fisher
 *
 */
public class FirstComeFirstServed extends Scheduler {

	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public FirstComeFirstServed(Requests requests, int numberOfRequests) {
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
		return "First-Come First-Served";
	}
}
