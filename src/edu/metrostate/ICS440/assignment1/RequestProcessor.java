package edu.metrostate.ICS440.assignment1;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestProcessor<T> extends Thread implements Lock {
	
	private static final AtomicInteger nextId = new AtomicInteger(0);
	private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			
			return nextId.getAndIncrement();
		}
	};
	
	private static ThreadLocal<Integer> localCount;
	
	private Queue<T> collection;
	private Queue<T> output;
	private ReentrantLock collectionLock;
	private ReentrantLock outputLock;
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
		this.output = new Queue<T>();
		this.collectionLock = new ReentrantLock();
		this.outputLock = new ReentrantLock();
		this.localCount = new ThreadLocal<Integer>();
	}
	
	private void process() throws InterruptedException {
		
		T item;
		
//		System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: before lock                      :: PROCESSOR :: " + LocalDateTime.now());
//		collectionLock.lock();
//		System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: after lock                       :: PROCESSOR :: " + LocalDateTime.now());
//		
//		try {
//			
//			// TODO: work implementation (this is the critical section)
//			
//			while (!collection.isEmpty()) {
//				
//				System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: I have the collection.           :: PROCESSOR :: " + LocalDateTime.now());
//				item = collection.dequeue();
//				
//				System.out.println();
//				System.out.println("  > Color: " + Color.values()[Integer.parseInt(item.toString())]);
//				System.out.println();
//				//Thread.sleep(10);
//				
//				if (localCount == null) {
//					
//					
//				}
//			}
//		}
//		
//		catch (Exception ex) {
//			
//			Thread.currentThread().interrupt();
//			ex.printStackTrace();
//		}
//		
//		finally {
//			
//			System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: before unlock                    :: PROCESSOR :: " + LocalDateTime.now());
//			collectionLock.unlock();
//			System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: I no longer have the collection. :: PROCESSOR :: " + LocalDateTime.now());
//			System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: after unlock                     :: PROCESSOR :: " + LocalDateTime.now());
//		}
		
		// TODO: This will not work - concurrency is not enforced...
		while (!collection.isEmpty()) {
			
			System.out.println("Thread ID: " + String.valueOf(threadId.get()) + " :: I have the collection.           :: PROCESSOR :: " + LocalDateTime.now());
			item = collection.dequeue();
			
			System.out.println();
			System.out.println("  > Color: " + Color.values()[Integer.parseInt(item.toString())]);
			System.out.println();
			//Thread.sleep(10);
			
			if (localCount == null) {
				
				
			}
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			process();
		}
		
		catch (InterruptedException ex) {
			
			ex.printStackTrace();
		}
	}

	@Override
	public void lock() {
		
		// TODO Auto-generated method stub
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		
		// TODO Auto-generated method stub
	}

	@Override
	public Condition newCondition() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryLock() {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		
		// TODO Auto-generated method stub
	}
}