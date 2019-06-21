package edu.metrostate.ICS462.assignment5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

 public class PagingAlgorithms {

 	private static final String OUTPUT_FILENAME = "Fisher_Shannon_ProgAssign5.txt";
	private static final int PAGE_REF_STRING_SIZE = 20;
	
	private static int[] randomPageReference = new int[PAGE_REF_STRING_SIZE];
	private static int[] secondPageReference = { 0, 7, 1, 1, 2, 0, 8, 9, 0, 3, 0, 4, 5, 6, 7, 0, 8, 9, 1, 2 };
	private static int[] thirdPageReference = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };

 	private StringBuilder stringBuilder;
 	
 	public PagingAlgorithms() {

 		this.stringBuilder = new StringBuilder();
 		
 		initialize();
	}

 	// **********************************************************************************************************
	// Private helper method for initialization.
	// 
	private void initialize() {

 		Random random = new Random();

 		{
			// Set the output file header information.
			stringBuilder.append("Shannon Fisher" + "\r\n");
			stringBuilder.append("ICS 462 Programming Assignment 5" + "\r\n" + "\r\n");
		}

 		// Build the random page reference string.
		for (int i = 0; i < PAGE_REF_STRING_SIZE; i++) {

 			randomPageReference[i] = random.nextInt(10);
		}
	}
	
 	public int firstInFirstOut(int[] referenceString, int pageSize) {

 		int faultCount = 0;
		int nextIndex = 0;
		int[] queue = new int[pageSize];

 		Arrays.fill(queue, -1);

 		for (int i = 0; i < referenceString.length; i++) {

 			boolean matchFound = false;
			int pageValue = referenceString[i];

 			for (int k = 0; k < queue.length; k++) {

 				int queueValue = queue[k];

 				matchFound = false;

 				if (queueValue == pageValue) {

 					matchFound = true;
					break;
				}
			}

 			if (!matchFound) {

 				queue[nextIndex] = referenceString[i];
				nextIndex = Math.floorMod((nextIndex + 1), queue.length);
				faultCount++;
			}
		}

 		return faultCount;
	}

 	public int leastRecentlyUsed(int[] referenceString, int pageSize) {

 		Deque<Integer> deque = new LinkedList<Integer>();
 		Integer found = null;
 		int faultCount = 0;
 		
		for (int k = 0; k < referenceString.length; k++) {
			
			int pageValue = referenceString[k];
			
			if (deque.contains(pageValue)) {
				
				Iterator<Integer> it = deque.iterator();
				
				while (it.hasNext()) {
					
					Integer nextItem = it.next();
					
					if (nextItem == pageValue) {
						found = nextItem;
						break;
					}
				}
				
				if (deque.remove(found)) {
					
					deque.addFirst(found);
				}
			}
			
			else {
				
				deque.addFirst(pageValue);
				faultCount++;
			}
		}
 		
 		return faultCount;
	}

 	// **********************************************************************************************************
	// Reference:
	// https://www.thecrazyprogrammer.com/2016/11/optimal-page-replacement-algorithm-c.html
	// 
	public int optimal(int[] referenceString, int pageSize) {

 		int faultCount = 0;
		int nextIndex = 0;
		int[] queue = new int[pageSize];
		
 		Arrays.fill(queue, -1);
 		
 		for (int i = 0; i < referenceString.length; i++) {

 			boolean matchFound = false;
			boolean vacant = false;
			int pageValue = referenceString[i];

 			for (int k = 0; k < queue.length; k++) {

 				int queueValue = queue[k];

 				matchFound = false;
				vacant = false;

 				if (queueValue == pageValue) {

 					matchFound = true;
					break;
				}

 				if (queueValue == -1) {

 					vacant = true;
				}
			}

 			if (vacant) {

 				queue[nextIndex] = referenceString[i];
				nextIndex = Math.floorMod((nextIndex + 1), queue.length);
				faultCount++;
				continue;
			}

 			if (!matchFound && !vacant) {

 				// testRefString = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };

 				int maxDistance = -1;
				int targetIndex = -1;

 				for (int queueIndex = 0; queueIndex < queue.length; queueIndex++) {

 					if (i == 10) {

 						String stop = "";
					}

 					int qValue = queue[queueIndex];
					int size = referenceString.length;
					int threshold = size - i;

 					for (int offsetIndex = i + 1; offsetIndex < size; offsetIndex++) {

 						int nextRefVal = referenceString[offsetIndex];

 						if (qValue == nextRefVal) {

 							if (offsetIndex - i > maxDistance) {

 								maxDistance = offsetIndex - i;
								targetIndex = queueIndex;
								break;
							}

 							else {

 								break;
							}
						}

 						else if (--threshold == 1) {

 							maxDistance = referenceString.length;
							targetIndex = queueIndex;
						}
					}
				}

 				if (targetIndex == -1) {

 					queue[nextIndex] = referenceString[i];
					nextIndex = Math.floorMod((nextIndex + 1), queue.length);
				}

 				else {

 					queue[targetIndex] = referenceString[i];
				}

 				faultCount++;
			}
		}

 		return faultCount;
	}

 	/************************************************************************************************************
	 * Method to write the program results to a file.
	 * <p>
	 * 
	 * @postcondition
	 *   The results of the paging algorithms.
	 * 
	 * @throws IOException
	 *   IOException is thrown if there is an I/O problem with the results file output.
	 */
	public void writeToFile() throws IOException {

 		// File printing and output setup.
		File file = new File(OUTPUT_FILENAME);
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = null;

 		try {

 			if (!file.exists()) {

 				file.createNewFile();
			}

 			printWriter = new PrintWriter(fileWriter);

 			// Print the contents of the results to the output file.
			printWriter.println(stringBuilder.toString());
		}

 		catch (IOException ex) {

 			ex.printStackTrace();
		}

 		finally {

 			printWriter.close();
		}
	}
	
 	public static void main(String[] args) {

 		PagingAlgorithms application = new PagingAlgorithms();

 		int[][] pageReferenceList = { randomPageReference, secondPageReference, thirdPageReference };

 		for (int i = 0; i < pageReferenceList.length; i++) {

 			for (int pageFrames = 1; pageFrames <= 7; pageFrames++) {

 				int fifoFaults;
				int lruFaults;
				int optimalFaults;
				
				int[] currentReferenceString = pageReferenceList[i];

 				fifoFaults = application.firstInFirstOut(currentReferenceString, pageFrames);
 				lruFaults =application.leastRecentlyUsed(currentReferenceString, pageFrames);
				optimalFaults = application.optimal(currentReferenceString, pageFrames);
				
				System.out.println("For " + pageFrames + " page frames, and using string page reference string: " + Arrays.toString(currentReferenceString));
				System.out.println();
				System.out.println("    FIFO had " + fifoFaults + " page faults.");
				System.out.println("     LRU had " + lruFaults + " page faults.");
				System.out.println("     OPT had " + optimalFaults + " page faults.");
				System.out.println();
			}
		}
	}
}
