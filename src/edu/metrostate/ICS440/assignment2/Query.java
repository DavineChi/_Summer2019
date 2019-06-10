package edu.metrostate.ICS440.assignment2;

public class Query {

	private int startYear;
	private int endYear;
	private int startMonth;
	private int endMonth;
	private String element;
	
	public Query(int startYear, int endYear, int startMonth, int endMonth, String element) {
		
		this.startYear = startYear;
		this.endYear = endYear;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.element = element;
	}

	public int getStartYear() {
		
		return startYear;
	}
	
	public int getEndYear() {
		
		return endYear;
	}
	
	public int getStartMonth() {
		
		return startMonth;
	}
	
	public int getEndMonth() {
		
		return endMonth;
	}
	
	public String getElement() {
		
		return element;
	}
	
	public boolean matches(WeatherData weatherData) {
		
		int wdYear = weatherData.getYear();
		int wdMonth = weatherData.getMonth();
		String wdElement = weatherData.getElement();
		
		boolean result = false;
		
		if (wdYear >= startYear && wdYear <= endYear &&
			wdMonth >= startMonth && wdMonth <= endMonth &&
			wdElement.equals(element)) {
			
			result = true;
		}
		
		return result;
	}
}
