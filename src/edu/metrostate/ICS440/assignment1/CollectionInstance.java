package edu.metrostate.ICS440.assignment1;

public class CollectionInstance {

	private static final ThreadLocal<Queue> collection = new ThreadLocal<Queue>() {
		
		protected Queue initialValue() {
			
			return new Queue();
		}
	};
	
	public static Queue get() {
		
		Thread.currentThread().getId();
		
		return collection.get();
	}
}
