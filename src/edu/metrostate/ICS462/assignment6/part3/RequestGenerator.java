package edu.metrostate.ICS462.assignment6.part3;

/**
 * Generates the cylinder requests.
 * 
 * @author Brahma Dathan
 *
 */
public class RequestGenerator extends Thread {
	private Requests requests;
	private int numberOfRequests;

	/**
	 * Stores the Requests object and number of requests.
	 * 
	 * @param requests        Requests object storing the cylinder requests
	 * @param maximumRequests number of requests
	 */
	public RequestGenerator(Requests requests, int maximumRequests) {
		this.requests = requests;
		this.numberOfRequests = maximumRequests;
	}

	/**
	 * Generates the requests for cylinders and ends.
	 */
	public void run() {
		for (int index = 0; index < numberOfRequests; index++) {
			requests.add(Integer.valueOf(((int) (Math.random() * 10000)) % Analyzer.NUMBER_OF_CYLINDERS));
			try {
				Thread.sleep(Integer.valueOf(((int) (Math.random() * 10000)) % Analyzer.DELAY_BETWEEN_REQUESTS));
			} catch (InterruptedException ie) {

			}
		}
	}
}
