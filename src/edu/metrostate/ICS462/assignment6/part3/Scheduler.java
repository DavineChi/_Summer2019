package edu.metrostate.ICS462.assignment6.part3;

import java.util.Vector;

/**
 * Implements or specifies the common aspects of all disk scheuling algorithms.
 * 
 * @author Brahma Dathan
 *
 */
public abstract class Scheduler extends Thread {
	protected Vector<Integer> tempRequests = new Vector<Integer>();
	protected Requests requests;
	protected int numberOfRequests;
	protected int processed = 0;
	protected Vector<Integer> newRequests;
	protected int currentPosition = 0;

//  add fields for maintaining statistics 
	/**
	 * Stores the Requests object and number of requests.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public Scheduler(Requests requests, int numberOfRequests) {
		this.requests = requests;
		this.numberOfRequests = numberOfRequests;
	}

	/**
	 * Initialize the relevant variables for the specific scheduling algorithm.
	 */
	public abstract void initialize();

	/**
	 * Each algorithm has a specific way of choosing the next request. After that
	 * some preparations have to be done before accepting the next request. This
	 * method does all that.
	 */
	public abstract void processNextRequest();

	/**
	 * Returns the name of the scheduling algorithm.
	 * 
	 * @return the name of the algorithm
	 */
	public abstract String getAlgorithmName();

	/**
	 * The common parts of the algorithm for all disk schedulers.
	 */
	public void run() {
		initialize();
		while (processed < numberOfRequests) {
			if (tempRequests.size() == 0) {
				newRequests = requests.get(true);
			} else {
				newRequests = requests.get(false);
			}
			tempRequests.addAll(newRequests);
			processNextRequest();
		}
		printStatistics(getAlgorithmName());
	}

	/**
	 * Simulates the processing of seeking to a track by sleeping for a specific
	 * period.The sleep time is quite approximate. A more sophisticated approach
	 * would make it proportional to the square root of the distance to be moved.
	 * 
	 * @param tracks number of tracks to be moved
	 * @return time slept
	 */
	public int sleep(int tracks) {
		if (tracks == 0) {
			return 0;
		}
		try {
			int time = Math.max(1, tracks * Analyzer.SWEEP_TIME / Analyzer.NUMBER_OF_CYCLINDERS);
			Thread.sleep(time);
			return time;
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		return 0;
	}

	/**
	 * Prints the statistics for a scheduling algorithm.
	 * 
	 * @param algorithm the name of the algorithm.
	 */
	public void printStatistics(String algorithm) {
		// print statistics
	}
}
