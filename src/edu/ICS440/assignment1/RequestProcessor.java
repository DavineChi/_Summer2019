package edu.ICS440.assignment1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RequestProcessor<T> extends Thread implements Lock {

	private Queue<T> collection;
	private Queue<T> output;
	
	// TODO: implementation
	
	public RequestProcessor(Queue<T> collection) {
		
		this.collection = collection;
	}

	private void process() {
		
		boolean value = collection.peek() != null;
		
		if (!value) {
			
			String stop = "STOP";
		}
		
		while (collection.size() != 0 && collection.peek() != null) {
			
			T i;
			
			lock();
			
			try {
				
				i = collection.dequeue();
			}
			
			finally {
				
				unlock();
			}
		}
	}
	
	@Override
	public void run() {
		
		process();
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