package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;

/****************************************************************************************************************
 * This class contains static methods used for debugging.
 * <p>
 * 
 * @author Shannon L. Fisher
 * <p>
 * Begin Date:	2019.05.23
 * <p>
 * Due Date:	2019.06.06
 */
public class Debug {
	
	private static final boolean ENABLED = false;
	
	/**
	 * A static method to write a message before a thread lock.
	 * 
	 */
	public static void beforeLock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: before lock            :: " + LocalDateTime.now());
		}
	}
	
	/**
	 * A static method to write a message after a thread lock.
	 * 
	 */
	public static void afterLock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: after lock             :: " + LocalDateTime.now());
		}
	}
	
	/**
	 * A static method to write a message before a thread unlock.
	 * 
	 */
	public static void beforeUnlock() {
		
		if (ENABLED) {
			
			System.out.println("Thread ID: " +
					String.valueOf(ThreadStatisticsSetup.threadId.get()) +
					" :: BEFORE UNLOCK          :: " + LocalDateTime.now());
		}
	}
	
	/**
	 * A static method to write a message after a thread unlock.
	 * 
	 */
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
