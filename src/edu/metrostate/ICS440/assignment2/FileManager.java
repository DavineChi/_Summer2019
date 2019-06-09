package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	/************************************************************************************************************
	 * An accessor method to retrieve a list of weather data filenames.
	 * <p>
	 * 
	 * @param pathname
	 *   the location from which the list of filenames are retrieved
	 * 
	 * @return
	 *   A list of weather data filenames.
	 */
	public static List<File> getFiles(String pathname) {
		
		List<File> result = new ArrayList<File>();
		
		File path = new File(pathname);
		
		for (File file : path.listFiles()) {
			
			result.add(file);
		}
		
		return result;
	}
}
