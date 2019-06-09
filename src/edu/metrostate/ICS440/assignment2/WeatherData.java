package edu.metrostate.ICS440.assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WeatherData {

    private String id;
    private int year;
    private int month;
    private int day;
    private String element;
    private int value;
    private String qflag;
    
    public String parseData(List<String> files) throws IOException {
    	
    	StringBuilder sb = new StringBuilder();
		
    	BufferedReader reader;
    	String line;
    	
    	for (String file : files) {
    		
    		// TODO: implementation
    		
    		try {
    			
				reader = new BufferedReader(new FileReader(file));
				
				while ((line = reader.readLine()) != null) {
					
					// TODO: implementation
				}
			}
    		
    		catch (FileNotFoundException ex) {
    			
				ex.printStackTrace();
			}
    	}
    	
		return sb.toString();
    }
    

	private void parseData2(List<File> files) {
		
		Scanner input;
		String nextLine;
		
		try {
			
			for (File file : files) {
				
				input = new Scanner(file);
				
				while (input.hasNextLine()) {
					
					nextLine = input.nextLine();
					
					String id = nextLine.substring(0,11);
					int year = Integer.valueOf(nextLine.substring(11,15).trim());
					int month = Integer.valueOf(nextLine.substring(15,17).trim());
					String element = nextLine.substring(17,21);
					int days = (nextLine.length() - 21) / 8; // Calculate the number of days in the line
				}
			}
		}
		
		catch (FileNotFoundException ex) {
			
			ex.printStackTrace();
		}
	}
    
    public static void main(String[] args) {
    	
    	WeatherData weatherData = new WeatherData();
    	
    	List<File> files = FileManager.getFiles("ghcnd_hcn");
    	
    	weatherData.parseData2(files);
    	
//    	try {
//    		
//			weatherData.parseData(files);
//		}
//    	
//    	catch (IOException ex) {
//    		
//			ex.printStackTrace();
//		}
    	
//		String id = thisLine.substring(0,11);
//    	int year = Integer.valueOf(thisLine.substring(11,15).trim());
//    	int month = Integer.valueOf(thisLine.substring(15,17).trim());
//    	String element = thisLine.substring(17,21);
//    	int days = (thisLine.length() - 21) / 8; // Calculate the number of days in the line
//    	
//    	for (int i = 0; i < days; i++) {         // Process each day in the line.
//    	   WeatherData wd = new WeatherData();
//    	   wd.day = i + 1;
//    	   int value = Integer.valueOf(thisLine.substring(21+8*i,26+8*i).trim());
//    	   String qflag = thisLine.substring(27+8*i,28+8*i);
//    	   wd.id = id;
//    	   wd.year = year;
//    	   wd.month = month;
//    	   wd.element = element;
//    	   wd.value = value;
//    	   wd.qflag = qflag;
//    	}
    }
}