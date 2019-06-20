package edu.metrostate.ICS440.assignment2;

/****************************************************************************************************************
 * This class contains static methods used for debugging.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class Debug {
	
	private static final boolean ENABLED = true;
	
	/************************************************************************************************************
	 * Writes a specified message to the console.
	 * <p>
	 * 
	 * @param message
	 *   the message to write to the console
	 * 
	 * @postcondition
	 *   A message has been written to the console.
	 */
	public static void printMessage(String message) {
		
		if (ENABLED) {
			
			System.out.println(message);
		}
	}
}
