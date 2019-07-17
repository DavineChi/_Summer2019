package com.mockup;

public class AtomicRegister {
	private boolean old;
	private long stamp;
	private UsefulWidget value;
	public static AtomicRegister MIN_VALUE = new AtomicRegister(null);
	public AtomicRegister(UsefulWidget init) {
		stamp = 0;
		value = init;
	}
	
	public AtomicRegister(long stamp, UsefulWidget value) {
		this.stamp = stamp;
		this.value = value;
	}
	
	public void write(boolean x) {
		if (value.get() != x) {
			value.flip();
			old = x;
		}
	}
	
	public boolean read() {
		return value.get();
	}
	
	public static AtomicRegister max(AtomicRegister x, AtomicRegister y) {
		if (x.stamp > y.stamp) {
			return x;
		} else {
			return y;
		}
	}
}
