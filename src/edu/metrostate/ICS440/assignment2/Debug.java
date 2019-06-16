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
	
	private static final boolean ENABLED = false;
	
	/************************************************************************************************************
	 * A static method to write a message before a thread lock.
	 * <p>
	 * 
	 * @param message
	 *   the message to print
	 */
	public static void printMessage(String message) {
		
		if (ENABLED) {
			
			System.out.println(message);
		}
	}
}
