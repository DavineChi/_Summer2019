package edu.metrostate.ICS440.sampleapp1;

import java.util.Iterator;

/**
 * A simple linked list to store transactions. Although no tail is needed, we store it for 
 * the sake of consistency with the third  version of the application. The transactions are
 * stored in no specific order.
 * @author mh6624pa
 *
 */
public class TransactionCollection {
	
	private Node head;
	private Node tail; // No need for this, but kept for consistency with the third version
	
	/**
	 * This class represents a single node of a linked list
	 * @author mh6624pa
	 *
	 */
	public class Node {
		
		private Transaction value;
		private Node next;
		
		/**
		 * Needs the transaction object and the next pointer.
		 * 
		 * @param value
		 * @param next
		 */
		public Node(Transaction value, Node next) {
			
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Needs the transaction object; next is set to null.
		 * 
		 * @param value
		 */
		public Node(Transaction value) {
			
			this(value, null);
		}
		
		// getters
		public Transaction getValue() {
			
			return value;
		}
		
		public Node getNext() {
			
			return next;
		}
		
		/**
		 * Sets the next field
		 * @param next
		 */
		public void setNext(Node next) {
			
			this.next = next;
		}
	}
	
	/**
	 * Inner class for implementing an Iterator<Transaction>
	 * 
	 * @author mh6624pa
	 *
	 */
	public class TransactionIterator implements Iterator <Transaction> {
		
		private TransactionCollection.Node current = head;
		
		/**
		 * Implements the hasNext method of Iterator.
		 * 
		 */
		@Override
		public boolean hasNext() {
			
			return current != null;
		}
		
		/**
		 * Implements the next method of Iterator.
		 * 
		 */
		@Override
		public Transaction next() {
			
			Transaction value = current.getValue();
			
			current = current.getNext();
			
			return value;
		}
		
		@Override
		/**
		 * We are not really implementing this for any functionality.
		 * So it is an empty method.
		 * 
		 */
		public void remove() {
			
			
		}
	}
	/**
	 * Adds a Transaction to the head of the queue.
	 * The method sleeps for a bit to  see if that would cause errors,
	 * 
	 * @param value
	 */
	public void add(Transaction value) {
		
		if (head != null) {
			
			Node newNode = new Node(value, head);
			
			try {
				
				Thread.sleep(((int) Math.random() * 1000) % 2);
			}
			
			catch (InterruptedException ie) {
				
			}
			
			head = newNode;
		}
		
		else {
			
			tail = new Node(value);
			/*	try {
            		Thread.sleep(((int) Math.random() * 1000) % 2);
            	} catch(InterruptedException ie) {

            	}*/
			head = tail;
		}
	}
	
	/**
	 * Adds aen entire collection to the end of the list. This allows easy concatenation.
	 * 
	 */
	public void add(TransactionCollection collection) {
		
		if (tail == null) {
			
			head = collection.getHead();
		}
		
		else {
			
			tail.setNext(collection.getHead());
		}
		
		tail = collection.getTail();
	}
	
	/**
	 * Returns the node at the front
	 */
	public Node getHead() {
		
		return head;
	}
	
	/**
	 * Returns the node at the end
	 */
	public Node getTail() {
		
		return tail;
	}
	/**
	 * Returns an iterator
	 * @return
	 */
	public TransactionIterator iterator() {
		
		return new TransactionIterator();
	}
	
	/**
	 * Laboriously counts the number of nodes. Note that incrementing a field
	 * would have been simpler,but that may not catch all the errors.
	 * @return
	 */
	public int size() {
		
		int size = 0;
		
		for (Node temp = head; temp != null; temp = temp.getNext(), size = size + 1);
		
		return size;
	}
}