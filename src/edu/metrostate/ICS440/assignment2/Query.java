package edu.metrostate.ICS440.assignment2;

import java.util.concurrent.ConcurrentLinkedQueue;

/****************************************************************************************************************
 * A class to encapsulate query information for use with weather data files.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class Query {

	private int startYear;
	private int endYear;
	private int startMonth;
	private int endMonth;
	private String element;
	
	/************************************************************************************************************
	 * Constructor used to create a new Query object.
	 * <p>
	 * 
	 * @postcondition
	 *   A new Query object has been created.
	 */
	public Query(int startYear, int endYear, int startMonth, int endMonth, String element) {
		
		this.startYear = startYear;
		this.endYear = endYear;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.element = element;
	}

	/************************************************************************************************************
	 * An accessor method to retrieve the start year for this Query.
	 * <p>
	 * 
	 * @return
	 *   The start year for this Query.
	 */
	public int getStartYear() {
		
		return startYear;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the end year for this Query.
	 * <p>
	 * 
	 * @return
	 *   The end year for this Query.
	 */
	public int getEndYear() {
		
		return endYear;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the start month for this Query.
	 * <p>
	 * 
	 * @return
	 *   The start year for this Query.
	 */
	public int getStartMonth() {
		
		return startMonth;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the end month for this Query.
	 * <p>
	 * 
	 * @return
	 *   The end month for this Query.
	 */
	public int getEndMonth() {
		
		return endMonth;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the element for this Query.
	 * <p>
	 * 
	 * @return
	 *   The element for this Query.
	 */
	public String getElement() {
		
		return element;
	}
	
	public ConcurrentLinkedQueue<WeatherData> retrieve(ConcurrentLinkedQueue<WeatherData> queue, int size) {
		
		ConcurrentLinkedQueue<WeatherData> result = null;
		
		switch (element) {
		
		case "TMAX":
			result = WeatherData.filterMax(queue, size);
			break;
			
		case "TMIN":
			result = WeatherData.filterMin(queue, size);
			break;
		}
		
		return result;
	}
	
	/************************************************************************************************************
	 * A method to determine if the specified data matches this Query.
	 * <p>
	 * 
	 * @param weatherData
	 *   the specified weather data to test against this Query
	 * 
	 * @return
	 *   True if the specified data matches this Query, false otherwise.
	 */
	public boolean matches(WeatherData weatherData) {
		
		int wdYear = weatherData.getYear();
		int wdMonth = weatherData.getMonth();
		String wdElement = weatherData.getElement();
		float wdValue = weatherData.getValue();
		String wdQflag = weatherData.getQflag();
		
		boolean result = false;
		
		if (wdYear >= startYear &&
			wdYear <= endYear &&
			wdMonth >= startMonth &&
			wdMonth <= endMonth &&
			wdElement.equals(element) &&
			wdValue != -999.9f &&
			wdQflag.equals(" ")) {
			
			result = true;
		}
		
		return result;
	}
}
