package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static Scanner input = new Scanner(System.in);
	
	private static int startYear;
	private static int endYear;
	private static int startMonth;
	private static int endMonth;
	
	private static String element;
	
	private static Query query;
	
	private static void getProgramInput() {
		
		System.out.println(" Enter a starting year...");
		System.out.print(" > ");
		startYear = Integer.parseInt(input.nextLine());
		
		System.out.println(" Enter an ending year: ");
		System.out.print(" > ");
		endYear = Integer.parseInt(input.nextLine());
		
		System.out.println(" Enter a starting month: ");
		System.out.print(" > ");
		startMonth = Integer.parseInt(input.nextLine());
		
		System.out.println(" Enter an ending month: ");
		System.out.print(" > ");
		endMonth = Integer.parseInt(input.nextLine());
		
		System.out.println(" Mininum (A) or Maximum (B) temperatures? ");
		System.out.print(" > ");
		element = input.nextLine().toUpperCase();
		
		if (element.equals("A")) {
			
			element = "TMIN";
		}
		
		else if (element.equals("B")) {
			
			element = "TMAX";
		}
		
		System.out.println("  Start year: " + startYear);
		System.out.println("    End year: " + endYear);
		System.out.println(" Start month: " + startMonth);
		System.out.println("   End month: " + endMonth);
		System.out.println("Temperatures: " + element);
		
		query = new Query(startYear, endYear, startMonth, endMonth, element);
	}
	
	private static void printResults() {
		
		// TODO: print the maximum (or minimum) five temperatures
		//       that occurred in the range of years and months
	}
	
	/************************************************************************************************************
	 * Main method from where program execution begins.
	 * <p>
	 * 
	 * @param args
	 *   this parameter is not used
	 * 
	 * @postcondition
	 *   ...
	 */
	public static void main(String[] args) {
		
		Query query = new Query(2001, 2005, 8, 12, "TMAX");
		
		WeatherData weatherData = new WeatherData();
		
		List<File> weatherFiles = FileManager.getWeatherFiles("ghcnd_hcn");
		
		Queue<WeatherData> weatherDataResults = weatherData.search(weatherFiles, query);
		
		//getProgramInput();
	}
}
