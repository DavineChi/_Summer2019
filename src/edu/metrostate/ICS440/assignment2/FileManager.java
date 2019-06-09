package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public static List<File> getWeatherFiles(String pathname) {
		
		List<File> result = new ArrayList<File>();
		
		File path = new File(pathname);
		
		for (File file : path.listFiles()) {
			
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
		
		for (File file : path.listFiles()) {
			
			if (file.getName().equals(fileName)) {
				
				result = file;
			}
		}
		
		return result;
	}
}
