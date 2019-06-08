package edu.metrostate.ICS440.assignment2;

public class WeatherData {

    String id;
    int year;
    int month;
    int day;
    String element;
    int value;
    String qflag;
    
    public static void main(String[] args) {
    	
    	String thisLine = null;
    	
		String id = thisLine.substring(0,11);
    	int year = Integer.valueOf(thisLine.substring(11,15).trim());
    	int month = Integer.valueOf(thisLine.substring(15,17).trim());
    	String element = thisLine.substring(17,21);
    	int days = (thisLine.length() - 21) / 8; // Calculate the number of days in the line
    	for (int i = 0; i < days; i++) {         // Process each day in the line.
    	   WeatherData wd = new WeatherData();
    	   wd.day = i + 1;
    	   int value = Integer.valueOf(thisLine.substring(21+8*i,26+8*i).trim());
    	   String qflag = thisLine.substring(27+8*i,28+8*i);
    	   wd.id = id;
    	   wd.year = year;
    	   wd.month = month;
    	   wd.element = element;
    	   wd.value = value;
    	   wd.qflag = qflag;
    	}

    }
}