package edu.metrostate.ICS440.sampleapp3;

/**
 * Each transaction is a financial transaction taking place at some site for a 
 * certain amount. All transactions are identified by a unique id.
 * Transactions are processed in stages. Initially, the stage is 0. 
 * 
 * @author mh6624pa
 *
 */
public class Transaction {
	
	private int id;
	private int stage;
	private double amount;
	private int site;
	
	/** 
	 * Initializes the transaction with a given id, stage, amount, and site
	 * @param id		should be unique
	 * @param stage		expected to be 0
	 * @param amount	transaction amount
	 * @param site		site where the transaction took place
	 */
	public Transaction(int id, int stage, double amount, int site) {
		
		this.id = id;
		this.stage = stage;
		this.amount = amount;
		this.site = site;
	}
	
	// getters
	public int getId() {
		
		return id;
	}
	
	public int getStage() {
		
		return stage;
	}
	
	public double getAmount() {
		
		return amount;
	}
	
	public int getSite() {
		
		return site;
	}
	
	public void setStage(int stage) {
		
		this.stage = stage;
	}
	
	/**
	 * This is a dummy process method, just put in to consume some processor time.
	 * It doesn't do anything particularly useful.
	 * 
	 */
	public void process() {
		
		if (stage == 0) {
			
			int k = 0;
			double l = 0;
			
			for (int i = 0; i < 10000; i++) {
				
				k = i;
				
				for (int j = 0; j < 100000; j++) {
					
					for (int m = 0; m < 2; m++) {
						
						l = k * 15.5 / 32.4;
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "Transaction [id=" + id + ", stage=" + stage + ", amount=" + amount + ", site=" + site + "]";
	}
	
	/**
	 * Two transactions are equal iff they have the same id. 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			
			return true;
		}
		
		if (obj == null) {
			
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			
			return false;
		}
		
		Transaction other = (Transaction)obj;
		
		if (id != other.id) {
			
			return false;
		}
		
		return true;
	}

}