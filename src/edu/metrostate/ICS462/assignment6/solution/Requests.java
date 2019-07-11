package edu.metrostate.ICS462.assignment6.solution;

import java.util.Vector;

/**
 * Stores all cyilcer requests that have not yet been read in by a disk
 * scheduler.
 * 
 * @author Brahma Dathan
 *
 */
public class Requests {
	private Vector<Integer> requests = new Vector<Integer>();

	/**
	 * Adds a cylinder to the list. This method is called by a thread that generates
	 * requests for cylinders. There may be a thread waiting on such an addition. So
	 * after adding, notify all such threads.
	 * 
	 * @param track the track number to be added
	 */
	public synchronized void add(Integer track) {
		requests.add(track);
		notifyAll();
	}

	/**
	 * Returns all requests that have been added to the Vector object, but have not
	 * been retrieved yet. This is called by a thread that simulates one of the disk
	 * scheduling algorithms to get the next set of disk requests. The requests are
	 * returned in the chronological order of generation. If there are no requests,
	 * the invoking thread waits if the Boolean parameter waitIfEmpty is true. If
	 * there are requests in the Vector object or waitIfEmpty is false, the calling
	 * thread does not wait.
	 * 
	 * @param waitIfEmpty if true, the calling thread waits until there is a
	 *                    request.
	 * @return a Vector of new requests for cylinders
	 */
	public synchronized Vector<Integer> get(boolean waitIfEmpty) {
		if (requests.size() == 0 && waitIfEmpty) {
			try {
				wait();
			} catch (InterruptedException ie) {
			}
		}
		Vector<Integer> newRequests = new Vector<Integer>();
		newRequests.addAll(requests);
		requests.clear();
		return newRequests;
	}
}
