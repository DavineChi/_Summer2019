package edu.metrostate.ICS440.assignment2;

/****************************************************************************************************************
 * Main class for program entry.
 * <p>
 * 
 * This application assumes that the directory "ghcnd_hcn" is on the classpath (or exists at the Java project's
 * root), and that this directory contains the "ghcnd-stations.txt" file as well as a series of "*.dly" weather
 * data files.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class Main {

	/************************************************************************************************************
	 * Main method from where program execution begins.
	 * <p>
	 * 
	 * @param args
	 *   parameter not used
	 * 
	 * @postcondition
	 *   The search application has been executed.
	 */
	public static void main(String[] args) {
		
		WeatherApp.run();
	}
}
