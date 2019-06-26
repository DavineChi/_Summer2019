package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/****************************************************************************************************************
 * This class is used to hold weather station data.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class StationData {
	
    private String id;
    private float latitude;
    private float longitude;
    private float elevation;
    private String state;
    private String name;
    
    private static ConcurrentLinkedQueue<StationData> stationQueue;
    
    /************************************************************************************************************
     * An accessor method to return the ID for this StationData.
     * <p>
     * 
     * @return
     *   This StationData's ID.
     */
    public String getId() {
    	
    	return id;
    }
    
    /************************************************************************************************************
     * A method to provide a list of matching weather stations from the given parameters.
     * <p>
     * 
     * @param file
     *   the station data file to search
     * 
     * @param queue
     *   the results of a search pass for weather data (contains the station data this method will return)
     * 
     * @return
     *   A list of weather stations matching the passed-in <CODE>queue</CODE> parameter.
     */
	public static ConcurrentLinkedQueue<StationData> search(File file, ConcurrentLinkedQueue<WeatherData> queue) {
		
		Scanner input;
		String nextLine;
		
		stationQueue = new ConcurrentLinkedQueue<StationData>();
		
		try {
			
			StationData stationData;
			
			input = new Scanner(file);
			
			// Begin parsing the station file for a collection of matching StationData items.
			while (input.hasNextLine()) {
				
				stationData = new StationData();
				
				nextLine = input.nextLine();
				
				stationData.id = nextLine.substring(0, 11);
				stationData.latitude = Float.valueOf(nextLine.substring(12, 20).trim());
				stationData.longitude = Float.valueOf(nextLine.substring(21, 30).trim());
				stationData.elevation = Float.valueOf(nextLine.substring(31, 37).trim());
				stationData.state = nextLine.substring(38, 40);
				stationData.name = nextLine.substring(41, 71).trim();
				
				// If this StationData's ID matches an ID in the specified queue, ...
				if (stationData.matches(queue)) {
					
					// ... then add this StationData item to a list.
					stationQueue.add(stationData);
				}
			}
		}
		
		catch (NumberFormatException | FileNotFoundException ex) {
			
			ex.printStackTrace();
		}
		
		return stationQueue;
	}
	
	/************************************************************************************************************
	 * A method to determine if the specified queue contains the ID in this StationData object.
	 * <p>
	 * 
	 * @param queue
	 *   the queue to test against this StationData object's ID
	 * 
	 * @return
	 *   True if the specified queue contains the ID in this StationData object, false otherwise.
	 */
	public boolean matches(ConcurrentLinkedQueue<WeatherData> queue) {
		
		boolean result = false;
		
		for (WeatherData item : queue) {
			
			String id = item.getId();
			
			if (this.id.equals(id)) {
				
				result = true;
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return "id=" + id + ", latitude=" + latitude + ", longitude=" + longitude +
				", elevation=" + elevation + ", state=" + state + ", name=" + name;
	}
}