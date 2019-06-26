package edu.metrostate.ICS440.assignment2;

/****************************************************************************************************************
 * This class contains methods used for debugging.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class Debug {
	
	private static boolean enabled = false;
	
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
		
		if (enabled) {
			
			System.out.println(message);
		}
	}
	
	/************************************************************************************************************
	 * A method to set the enabled/disabled state for debugging.
	 * <p>
	 * 
	 * @param value
	 *   the state to which debugging is set
	 * 
	 * @postcondition
	 *   Debugging state is set to the passed-in <CODE>value</CODE> parameter.
	 */
	public static void enabled(boolean value) {
		
		enabled = value;
	}
}
