package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;

public class Debug {
	
	private static final boolean ENABLED = false;
	
	public static void beforeLock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
					" :: before lock            :: " + LocalDateTime.now());
		}
	}
	
	public static void afterLock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
					" :: after lock             :: " + LocalDateTime.now());
		}
	}
	
	public static void beforeUnlock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
					" :: BEFORE UNLOCK          :: " + LocalDateTime.now());
		}
	}
	
	public static void afterUnlock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
					" :: AFTER UNLOCK           :: " + LocalDateTime.now());
		}
	}
	
	public static void lockOwner() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
					" :: I have the collection. :: " + LocalDateTime.now());
		}
	}
}
