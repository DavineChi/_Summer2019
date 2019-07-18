package com.mockup.consensus;

public abstract class ConsensusProtocol<T> implements Consensus<T> {
	protected T[] proposed = (T[])new Object[2];
	public void propose(T value) {
		proposed[(int)Thread.currentThread().getId()] = value;
	}
	public abstract T decide(T value);
}
