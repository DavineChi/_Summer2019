package com.mockup.consensus;

public interface Consensus<T> {
	public T decide(T value);
}
