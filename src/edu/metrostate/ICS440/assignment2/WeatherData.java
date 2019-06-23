package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/****************************************************************************************************************
 * This class is used to hold and process weather data.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class WeatherData {

	private String id;
	private int year;
	private int month;
	private int day;
	private String element;
	private float value;
	private String qflag;
	
	/************************************************************************************************************
	 * An accessor method to retrieve the weather station ID.
	 * <p>
	 * 
	 * @return
	 *   The weather station ID.
	 */
	public String getId() {
		
		return id;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the year.
	 * <p>
	 * 
	 * @return
	 *   The year of this WeatherData record.
	 */
	public int getYear() {
		
		return year;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the month.
	 * <p>
	 * 
	 * @return
	 *   The month of this WeatherData record.
	 */
	public int getMonth() {
		
		return month;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the day.
	 * <p>
	 * 
	 * @return
	 *   The day of this WeatherData record.
	 */
	public int getDay() {
		
		return day;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the element type.
	 * <p>
	 * 
	 * @return
	 *   The element type.
	 */
	public String getElement() {
		
		return element;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the value on the first day of the month.
	 * <p>
	 * 
	 * @return
	 *   The value on the first day of the month.
	 */
	public float getValue() {
		
		return value;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve the quality flag for the first day of the month.
	 * <p>
	 * 
	 * @return
	 *   The quality flag for the first day of the month.
	 */
	public String getQflag() {
		
		return qflag;
	}
	
	public static ConcurrentLinkedQueue<WeatherData> search(File file, Query query, Integer threadId) {
		
		Scanner input = null;
		String nextLine;
		ConcurrentLinkedQueue<WeatherData> queue = new ConcurrentLinkedQueue<WeatherData>();
		
		try {
			
			input = new Scanner(file);
			
			Debug.printMessage("Analyzing file: " + file.getName() + " using Thread ID " + threadId);
			
			while (input.hasNextLine()) {

				nextLine = input.nextLine();

				String id = nextLine.substring(0,11);
				int year = Integer.valueOf(nextLine.substring(11,15).trim());
				int month = Integer.valueOf(nextLine.substring(15,17).trim());
				String element = nextLine.substring(17,21);
				int days = (nextLine.length() - 21) / 8;     // Number of days in the line

				WeatherData weatherData;

				for (int j = 0; j < days; j++) {             // Process each day in the line
					
					weatherData = new WeatherData();
					
					weatherData.day = j + 1;
					
					float value = Float.valueOf(nextLine.substring(21 + 8 * j, 26 + 8 * j).trim()) / 10.0f;
					String qflag = nextLine.substring((27 + 8 * j), (28 + 8 * j));
					
					weatherData.id = id;
					weatherData.year = year;
					weatherData.month = month;
					weatherData.element = element;
					weatherData.value = value;
					weatherData.qflag = qflag;
					
					if (query.matches(weatherData)) {
						
						queue.add(weatherData);
					}
				}
			}
		}
		
		catch (FileNotFoundException ex) {

			ex.printStackTrace();
		}
		
		if (queue.isEmpty()) {
			
			queue = null;
		}
		
		return queue;
	}
	
	public static ConcurrentLinkedQueue<WeatherData> filterMax(ConcurrentLinkedQueue<WeatherData> queue, int threshold) {
		
		ConcurrentLinkedQueue<WeatherData> result = new ConcurrentLinkedQueue<WeatherData>();
		WeatherData largestWeatherDataItem = null;
		
		float[] ignoreList = new float[threshold];
		
		Arrays.fill(ignoreList, -1.0f);
		
		int counter = 0;
		
		while (counter < threshold) {
			
			largestWeatherDataItem = getLargest(queue, ignoreList);
			ignoreList[counter] = largestWeatherDataItem.getValue();
			
			result.add(largestWeatherDataItem);
			
			counter++;
		}
		
		return result;
	}
	
	public static ConcurrentLinkedQueue<WeatherData> filterMin(ConcurrentLinkedQueue<WeatherData> queue, int threshold) {
		
		//WeatherApp.printResults(queue);
		
		ConcurrentLinkedQueue<WeatherData> result = new ConcurrentLinkedQueue<WeatherData>();
		WeatherData smallestWeatherDataItem = null;
		
		float[] ignoreList = new float[threshold];
		
		Arrays.fill(ignoreList, -1.0f);
		
		int counter = 0;
		
		while (counter < threshold) {
			
			smallestWeatherDataItem = getSmallest(queue, ignoreList);
			ignoreList[counter] = smallestWeatherDataItem.getValue();
			
			result.add(smallestWeatherDataItem);
			
			counter++;
		}
		
		return result;
	}
	
	private static WeatherData getLargest(ConcurrentLinkedQueue<WeatherData> queue, float[] ignore) {
		
		WeatherData result = null;
		float largest = -9999.9f;
		
		for (WeatherData item : queue) {
			
			float wdValue = item.getValue();
			boolean matchFound = false;
			
			for (int i = 0; i < ignore.length; i++) {
				
				if (wdValue == ignore[i]) {
					
					matchFound = true;
				}
			}
			
			if (wdValue > largest  && !matchFound) {
				
				largest = wdValue;
				result = item;
			}
		}
		
		return result;
	}
	
	private static WeatherData getSmallest(ConcurrentLinkedQueue<WeatherData> queue, float[] ignore) {
		
		WeatherData result = null;
		float smallest = 9999.9f;
		
		for (WeatherData item : queue) {
			
			float wdValue = item.getValue();
			boolean matchFound = false;
			
			for (int i = 0; i < ignore.length; i++) {
				
				if (wdValue == ignore[i]) {
					
					matchFound = true;
				}
			}
			
			if (wdValue < smallest && !matchFound) {
				
				smallest = wdValue;
				result = item;
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return "id=" + id + " year=" + year + " month=" + month + " day=" + day +
               " element=" + element + " value=" + value + "C" + " qflag=" + qflag;
	}
}