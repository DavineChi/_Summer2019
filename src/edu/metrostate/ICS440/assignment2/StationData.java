package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
class StationData {
	
    private String id;
    private float latitude;
    private float longitude;
    private float elevation;
    private String state;
    private String name;
    
    private static Queue<StationData> stationQueue;
    
	public Queue<StationData> parseData(File file) {

		Scanner input;
		String nextLine;
		
		stationQueue = new Queue<StationData>();
		
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
				stationData.name = nextLine.substring(41, 71);
				
				stationQueue.enqueue(stationData);
			}
		}
		
		catch (NumberFormatException | FileNotFoundException ex) {
			
			ex.printStackTrace();
		}
		
		return stationQueue;
	}

    
	@Override
	public String toString() {
		
		return "StationData [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", elevation="
				+ elevation + ", state=" + state + ", name=" + name + "]";
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

		StationData stationData = new StationData();

		File stationFile = FileManager.getStationFile("ghcnd_hcn", "ghcnd-stations.txt");
		
		//stationData.parseData(stationFile);
		
		Queue<StationData> queue = stationData.parseData(stationFile);
		
		String stop = "STOP";
	}
}