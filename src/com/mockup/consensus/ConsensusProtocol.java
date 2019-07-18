package com.mockup.consensus;

public abstract class ConsensusProtocol<T> implements Consensus<T> {
	protected T[] proposed = (T[])new Object[2];
	/**
	 * A shared array of proposed input values.
	 * @param value
	 *   the proposed value to store
	 */
	public void propose(T value) {
		proposed[(int)Thread.currentThread().getId()] = value;
	}
	public abstract T decide(T value);
}
