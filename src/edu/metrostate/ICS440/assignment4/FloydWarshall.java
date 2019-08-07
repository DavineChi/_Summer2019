package edu.metrostate.ICS440.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/****************************************************************************************************************
 * Main class for program entry.
 * <p>
 * 
 * This program executes the Floyd-Warshall algorithm on a 5000 by 5000 adjacency matrix.
 * The assignment goal is to design, develop, and implement a parallel solution using the
 * partitioning, communication, agglomeration, and mapping (PCAM) design paradigm.
 * 
 * <p>
 * Begin Date:	2019.07.25
 * <p>
 * Due Date:	2019.08.08
 */
public class FloydWarshall implements Callable {
	
	private static List<Future<ConcurrentLinkedQueue>> futuresList = new ArrayList<Future<ConcurrentLinkedQueue>>();
	
	private static ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_POOL_SIZE);
	
	private static final int INFINITY = Integer.MAX_VALUE; // Infinity
	private static final int DIMENSION = 500;  // TODO: original value = 5000
	
	private static double fill = 0.3;
	private static int maxDistance = 100;
	private static int adjacencyMatrix[][] = new int[DIMENSION][DIMENSION];
	private static int d[][] = new int[DIMENSION][DIMENSION];
	
	// **********************************************************************************************************
	// Helper method to generate a randomized matrix to use for the algorithm.
	// 
	private static void generateMatrix() {
		
		Random random = new Random();
		
		for (int i = 0; i < DIMENSION; i++) {
			
			for (int j = 0; j < DIMENSION; j++) {
				
				if (i != j) {
					
					adjacencyMatrix[i][j] = INFINITY;
				}
			}
		}
		
		for (int i = 0; i < DIMENSION * DIMENSION * fill; i++) {
			
			adjacencyMatrix[random.nextInt(DIMENSION)][random.nextInt(DIMENSION)] = random.nextInt(maxDistance + 1);
		}
	}
	
	// **********************************************************************************************************
	// Helper method to execute Floyd Warshall on adjacencyMatrix.
	// 
	private static void execute() {
		
		for (int i = 0; i < DIMENSION; i++) {
			
			for (int j = 0; j < DIMENSION; j++) {
				
				d[i][j] = adjacencyMatrix[i][j];
				
				if (i == j) {
					
					d[i][j] = 0;
				}
			}
		}
		
		for (int k = 0; k < DIMENSION; k++) {
			
			for (int i = 0; i < DIMENSION; i++) {
				
				for (int j = 0; j < DIMENSION; j++) {
					
					if (d[i][k] == INFINITY || d[k][j] == INFINITY) {
						
						continue;
					}
					
					else if (d[i][j] > d[i][k] + d[k][j]) {
						
						d[i][j] = d[i][k] + d[k][j];
					}
				}
			}
			
			//System.out.println("pass " + (k + 1) + "/" + dim);
		}
	}
	
	// **********************************************************************************************************
	// Helper method to print matrix[dim][dim]
	// 
	private static void print(int matrix[][]) {
		
		for (int i = 0; i < DIMENSION; i++) {
			
			for (int j = 0; j < DIMENSION; j++) {
				
				if (matrix[i][j] == INFINITY) {
					
					System.out.print("I" + " ");
				}
				
				else {
					
					System.out.print(matrix[i][j] + " ");
				}
			}
			
			System.out.println();
		}
	}
	
	// **********************************************************************************************************
	// Helper method to compare two matrices, matrix1[dim][dim] and matrix2[dim][dim]
	// and print whether they are equivalent.
	// 
	private static void compare(int matrix1[][], int matrix2[][]) {
		
		for (int i = 0; i < DIMENSION; i++) {
			
			for (int j = 0; j < DIMENSION; j++) {
				
				if (matrix1[i][j] != matrix2[i][j]) {
					
					System.out.println("Comparison failed.");
				}
			}
		}
		
		System.out.println("Comparison succeeded.");
	}
	
	@Override
	public Object call() throws Exception {
		
		return null;
	}
	
	/************************************************************************************************************
	 * Main method from where program execution begins.
	 * <p>
	 * 
	 * @param args
	 *   parameter not used
	 * 
	 * @postcondition
	 *   The application has been executed.
	 */
	public static void main(String[] args) {
		
		long start;
		long end;
		
		generateMatrix();
		
		start = System.nanoTime();
		
		execute();
		
		end = System.nanoTime();
		
		System.out.println("Time consumed: " + (double)(end - start) / 1000000000);
		print(adjacencyMatrix); // TODO: remove
		compare(d, d);
	}
}