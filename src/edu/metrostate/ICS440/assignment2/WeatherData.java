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
	
	public void parseData(List<File> files) {

		Scanner input;
		String nextLine;
		Queue<WeatherData> queue = new Queue<WeatherData>();
		
		try {

			for (File file : files) {

				input = new Scanner(file);

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
							
							queue.enqueue(weatherData);
						}
					}
				}
			}
		}

		catch (FileNotFoundException ex) {

			ex.printStackTrace();
		}
	}

	@Override
	public String toString() {
		
		return "WeatherData [id=" + id + ", year=" + year + ", month=" + month + ", day=" + day + ", element=" + element
				+ ", value=" + value + ", qflag=" + qflag + "]";
	}
	
	/************************************************************************************************************
	 * Main method from where program execution begins. Used here for testing and debugging.
	 * <p>
	 * 
	 * @param args
	 *   this parameter is not used
	 * 
	 * @postcondition
	 *   Varies depending on testing.
	 */
	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();

		List<File> weatherFiles = FileManager.getWeatherFiles("ghcnd_hcn");

		weatherData.parseData(weatherFiles);
	}
}