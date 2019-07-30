package edu.metrostate.ICS462.assignment6.part4;

public class FirstComeFirstServed extends Scheduler {

	public FirstComeFirstServed(Requests requests, int numberOfRequests) {
		
		super(requests, numberOfRequests);
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void processNextRequest() {
		
	}

	@Override
	public String getAlgorithmName() {
		return "First-Come First-Served";
	}
}
