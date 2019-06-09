package edu.metrostate.ICS440.assignment2;

public class Query {

	private int startYear;
	private int endYear;
	private int startMonth;
	private int endMonth;
	private String temperatures;
	
	public Query(int startYear, int endYear, int startMonth, int endMonth, String temperatures) {
		
		this.startYear = startYear;
		this.endYear = endYear;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.temperatures = temperatures;
	}

	public int getStartYear() {
		
		return startYear;
	}

	public void setStartYear(int startYear) {
		
		this.startYear = startYear;
	}

	public int getEndYear() {
		
		return endYear;
	}

	public void setEndYear(int endYear) {
		
		this.endYear = endYear;
	}

	public int getStartMonth() {
		
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		
		this.startMonth = startMonth;
	}

	public int getEndMonth() {
		
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		
		this.endMonth = endMonth;
	}

	public String getTemperatures() {
		
		return temperatures;
	}

	public void setTemperatures(String temperatures) {
		
		this.temperatures = temperatures;
	}
	
	public boolean match(int year, int month) {
		
		// TODO: implementation
		
		return false;
	}
}
