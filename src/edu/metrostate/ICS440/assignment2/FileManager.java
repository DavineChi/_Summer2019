package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ConcurrentLinkedQueue;

/****************************************************************************************************************
 * This class is used to simplify retrieval of data files.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.06.07
 * <p>
 * Due Date:	2019.06.27
 */
public class FileManager {
	
	/************************************************************************************************************
	 * An accessor method to retrieve a list of files.
	 * <p>
	 * 
	 * @param pathname
	 *   the resource path from which the list of files are retrieved
	 * 
	 * @return
	 *   A list of weather data files.
	 */
	public static ConcurrentLinkedQueue<File> getWeatherFilesQueue(String pathname) {
		
		ConcurrentLinkedQueue<File> result = new ConcurrentLinkedQueue<File>();
		File path = new File(pathname);
		
		FileFilter filter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				
				// Filter only on ".dly" weather files.
				return pathname.getName().endsWith(".dly");
			}
		};
		
		// Check if the "ghcnd_hcn" path exists before proceeding.
		if (path.listFiles() == null) {
			
			System.out.println("Path not found: " + pathname + " - exiting now.");
			System.exit(1);
		}
		
		// Add each ".dly" file from the file system (using the FileFilter) to a list.
		for (File file : path.listFiles(filter)) {
			
			result.add(file);
		}
		
		return result;
	}
	
	/************************************************************************************************************
	 * An accessor method to retrieve a specific file from a given path.
	 * <p>
	 * 
	 * @param pathname
	 *   the resource path from which the file is retrieved
	 * 
	 * @param fileName
	 *   the name of the file to retrieve
	 * 
	 * @return
	 *   A specific station data file.
	 */
	public static File getStationFile(String pathname, String fileName) {
		
		File result = null;
		File path = new File(pathname);
		
		// Check if the "ghcnd_hcn" path exists before proceeding.
		if (path.listFiles() == null) {
			
			System.out.println("Path not found: " + pathname + " - exiting now.");
			System.exit(1);
		}
		
		for (File file : path.listFiles()) {
			
			if (file.getName().equals(fileName)) {
				
				result = file;
			}
		}
		
		return result;
	}
}
