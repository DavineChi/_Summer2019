package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;

public class Debug {
	
	private static final boolean ENABLED = false;
	
	public static void beforeLock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: before lock            :: " + LocalDateTime.now());
		}
	}
	
	public static void afterLock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: after lock             :: " + LocalDateTime.now());
		}
	}
	
	public static void beforeUnlock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: BEFORE UNLOCK          :: " + LocalDateTime.now());
		}
	}
	
	public static void afterUnlock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: AFTER UNLOCK           :: " + LocalDateTime.now());
		}
	}
	
	public static void lockOwner() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: I have the collection. :: " + LocalDateTime.now());
		}
	}
}
