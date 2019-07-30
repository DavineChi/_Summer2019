package edu.metrostate.ICS462.assignment6.part4;

/**
 * Isolates the way the implementations of the different algorithms are
 * instantiated.
 * 
 * @author Brahma Dathan
 *
 */
public class AlgorithmFactory {
	private static AlgorithmFactory algorithmFactory;

	/**
	 * For instilling the singleton property.
	 */
	private AlgorithmFactory() {
	}

	/**
	 * Returns the singleton instance.
	 * 
	 * @return the singleton instance
	 */
	public static AlgorithmFactory instance() {
		if (algorithmFactory == null) {
			algorithmFactory = new AlgorithmFactory();
		}
		return algorithmFactory;
	}

	/**
	 * Returns one of the differet scheduler implementations
	 * 
	 * @param index            0 for Look, 1 for C-Look, 2 for SSTF, 3 for FCFS
	 * @param requests         the object in which cylinder requests are stored
	 * @param numberOfRequests number of cylinder requests
	 * @return a Scheduler algorithm as determined by index
	 */
	public Scheduler getScheduler(int index, Requests requests, int numberOfRequests) {
		switch (index) {
		case 0:
			return new Look(requests, numberOfRequests);
		case 1:
			return new CircularLook(requests, numberOfRequests);
		case 2:
			return new ShortestSeekTimeFirst(requests, numberOfRequests);
		case 3:
			return new FirstComeFirstServed(requests, numberOfRequests);
		}
		return null;
	}
}
