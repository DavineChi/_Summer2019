package edu.metrostate.ICS462.assignment6.part3;

/**
 * The main class that invokes all algorithms.
 * 
 * @author Brahma Dathan
 *
 */
public class Analyzer {
	public static final int DELAY_BETWEEN_REQUESTS = 1;
	public static final int NUMBER_OF_CYLINDERS = 1024;
	public static final int SWEEP_TIME = 30;
	private Requests requests = new Requests();

	/**
	 * Inbokes each scheduling algorithm in turn. After invoking an algorithm, the
	 * process waits until both the request generator and the scheduler threads
	 * complete.
	 * 
	 * @param numberOfRequests number of requests to be processed.
	 */
	private void process(int numberOfRequests) {
		for (int index = 0; index < 1; index++) {
			RequestGenerator generator = new RequestGenerator(requests, numberOfRequests);
			Scheduler scheduler = AlgorithmFactory.instance().getScheduler(index, requests, numberOfRequests);
			generator.start();
			scheduler.start();
			try {
				generator.join();
				scheduler.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Starts up the Analzyer with the number of requests accepted from the user.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		//System.out.print("Enter number of requests: ");
		//Scanner scanner = new Scanner(System.in);
		//int numberOfRequests = scanner.nextInt();
		//scanner.close();
		new Analyzer().process(200);
	}

}
