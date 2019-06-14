package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
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
	private int value;
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
	
	public static Queue<WeatherData> search(List<File> files, Query query) {
		
		Scanner input;
		String nextLine;
		Queue<WeatherData> queue = new Queue<WeatherData>();
		
		try {

			for (File file : files) {

				input = new Scanner(file);

				System.out.println("Analyzing file: " + file.getName());
				
				while (input.hasNextLine()) {

					nextLine = input.nextLine();

					String id = nextLine.substring(0,11);
					int year = Integer.valueOf(nextLine.substring(11,15).trim());
					int month = Integer.valueOf(nextLine.substring(15,17).trim());
					String element = nextLine.substring(17,21);
					int days = (nextLine.length() - 21) / 8; // Number of days in the line

					WeatherData weatherData;

					for (int i = 0; i < days; i++) { // Process each day in the line
						
						weatherData = new WeatherData();
						
						weatherData.day = i + 1;
						
						int value = Integer.valueOf(nextLine.substring(21+8*i,26+8*i).trim());
						String qflag = nextLine.substring((27 + 8 * i), (28 + 8 * i));
						
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
		
		return "WeatherData [id=" + id + ", year=" + year + ", month=" + month +
				", day=" + day + ", element=" + element + ", value=" + value +
				", qflag=" + qflag + "]";
	}
}