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
    
    public String getId() {
    	
    	return id;
    }
    
	public static ConcurrentLinkedQueue<StationData> search(File file, ConcurrentLinkedQueue<WeatherData> queue) {
		
		Scanner input;
		String nextLine;
		
		stationQueue = new ConcurrentLinkedQueue<StationData>();
		
		try {
			
			StationData stationData;
			
			input = new Scanner(file);

			while (input.hasNextLine()) {

				stationData = new StationData();
				
				nextLine = input.nextLine();
				
				stationData.id = nextLine.substring(0, 11);
				stationData.latitude = Float.valueOf(nextLine.substring(12, 20).trim());
				stationData.longitude = Float.valueOf(nextLine.substring(21, 30).trim());
				stationData.elevation = Float.valueOf(nextLine.substring(31, 37).trim());
				stationData.state = nextLine.substring(38, 40);
				stationData.name = nextLine.substring(41, 71).trim();
				
				if (stationData.matches(queue)) {
					
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