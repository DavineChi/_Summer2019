package edu.metrostate.ICS440.sampleapp3;

/**
 * Provides a local copy of the TransactionCollection class.
 * 
 * @author mh6624pa
 *
 */
public class CollectionInstance {
	
	private static final ThreadLocal < TransactionCollection > collection = new ThreadLocal < TransactionCollection > () {
		
		@Override
		protected TransactionCollection initialValue() {
			
			return new TransactionCollection();
		}
	};

	/**
	 * Returns one instance of the TransactionCollection class
	 * @return
	 */
	public static TransactionCollection get() {
		
		return collection.get();
	}
}