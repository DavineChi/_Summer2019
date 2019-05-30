package edu.metrostate.ICS440.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Constants {

	public static final AtomicInteger NEXT_ID = new AtomicInteger(0);
	public static final ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return NEXT_ID.getAndIncrement();
		}
	};
	
	public static final ThreadLocal<List<Integer>> LOCAL_COUNT = new ThreadLocal<List<Integer>>() {
		
		@Override
		protected List<Integer> initialValue() {
			
			List<Integer> list = new ArrayList<Integer>();
			
			// Fill the list with initial values (also sets the list's dimensions).
			for (int i = 0; i < Color.values().length; i++) {
				
				list.add(0);
			}
			
			return list;
		}
	};
}
