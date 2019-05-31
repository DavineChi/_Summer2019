package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;

public interface Debug {
	
	public static void beforeLock() {
		
		System.out.println("Thread ID: " +
				String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
				" :: before lock             :: " + LocalDateTime.now());
	}
	
	public static void afterLock() {
		
		System.out.println("Thread ID: " +
				String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
				" :: after lock              :: " + LocalDateTime.now());
	}
	
	public static void beforeUnlock() {
		
		System.out.println("Thread ID: " +
				String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
				" :: before unlock           :: " + LocalDateTime.now());
	}
	
	public static void afterUnlock() {
		
		System.out.println("Thread ID: " +
				String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
				" :: after unlock            :: " + LocalDateTime.now());
	}
	
	public static void lockOwner() {
		
		System.out.println("Thread ID: " +
				String.valueOf(ThreadStatisticsSetup.THREAD_ID.get()) +
				" :: I have the collection.  :: " + LocalDateTime.now());
	}
}
