package edu.metrostate.ICS440.assignment2;

import java.util.Scanner;

public class Main {
	
	private static Scanner input = new Scanner(System.in);
	
	private static int startYear;
	private static int endYear;
	private static int startMonth;
	private static int endMonth;
	
	private static String temperatures;
	
	private static void getProgramInput() {
		
		System.out.print(" > Enter a starting year: ");
		startYear = Integer.parseInt(input.nextLine());
		
		System.out.print(" > Enter an ending year: ");
		endYear = Integer.parseInt(input.nextLine());
		
		System.out.print(" > Enter a starting month: ");
		startMonth = Integer.parseInt(input.nextLine());
		
		System.out.print(" > Enter an ending month: ");
		endMonth = Integer.parseInt(input.nextLine());
		
		System.out.print(" > Mininum (A) or Maximum (B) temperatures? ");
		temperatures = input.nextLine().toUpperCase();
		
		if (temperatures.equals("A")) {
			
			temperatures = "TMIN";
		}
		
		else if (temperatures.equals("B")) {
			
			temperatures = "TMAX";
		}
		
		System.out.println("  Start year: " + startYear);
		System.out.println("    End year: " + endYear);
		System.out.println(" Start month: " + startMonth);
		System.out.println("   End month: " + endMonth);
		System.out.println("Temperatures: " + temperatures);
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
		
		startYear = 2001;
		endYear = 2005;
		startMonth = 8;
		endMonth = 12;
		temperatures = "TMAX";
		
		//getProgramInput();
	}
}
