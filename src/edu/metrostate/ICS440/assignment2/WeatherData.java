package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/****************************************************************************************************************
 * This class is used to hold weather data.
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
	
	public int getYear() {
		
		return year;
	}
	
	public int getMonth() {
		
		return month;
	}
	
	public String getElement() {
		
		return element;
	}
	
	public static Queue<WeatherData> search(Queue<File> files, Query query, Integer threadId) {
		
		Scanner input;
		String nextLine;
		Queue<WeatherData> queue = new Queue<WeatherData>();
		
		try {
			
			for (int i = 0; i < files.size(); i++) {
				
				File file = files.dequeue();
				
				input = new Scanner(file);

				System.out.println("Analyzing file: " + file.getName() + " using Thread ID " + threadId);
				
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
						
						if (qflag.equals(" ")) {
							
							if (query.matches(weatherData)) {
								
								queue.enqueue(weatherData);
							}
						}
					}
				}
			}
		}
		
		catch (FileNotFoundException ex) {

			ex.printStackTrace();
		}
		
		return queue;
	}
	
	@Override
	public String toString() {
		
		return "id=" + id + " year=" + year + " month=" + month + " day=" + day +
               " element=" + element + " value=" + value + "C" + " qflag=" + qflag;
	}
}