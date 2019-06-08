package edu.metrostate.ICS440.assignment2;

import java.io.File;
import java.io.IOException;
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
	public static List<String> getFiles(String pathname) {
		
		List<String> result = new ArrayList<>();
    	File path = new File(pathname);
    	String[] files = path.list();
    	String basePath = "";
    	
    	try {
    		
    		basePath = path.getCanonicalPath() + "\\";
		}
    	
    	catch (IOException ex) {
    		
			ex.printStackTrace();
		}
    	
    	for (String file : files) {
    		
    		result.add(basePath + file);
    	}
    	
		return result;
	}
}
