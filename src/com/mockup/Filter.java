package com.mockup;

public class Filter {

	int[] level;
	int[] victim;
	
	public Filter(int n) {
		
		level = new int[n];
		victim = new int[n];
		
		for (int i = 0; i < n; i++) {
			
			level[i] = 0;
		}
		
		String temp = "";
	}
	
	public static void main(String[] args) {
		
		Filter filter = new Filter(5);
	}
}
