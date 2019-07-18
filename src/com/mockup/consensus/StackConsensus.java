package com.mockup.consensus;

import java.util.Stack;

public class StackConsensus<T> extends ConsensusProtocol<T> {
	private static final int WIN = 0;   // first thread
	private static final int LOSE = 1;  // second thread
	private Stack<Integer> stack;
	public StackConsensus() {
		stack = new Stack<Integer>();
		stack.push(LOSE);
		stack.push(WIN);
	}
	@Override
	public T decide(T value) {  // determine which thread went first
		propose(value);
		int status = stack.pop();
		int i = (int)Thread.currentThread().getId();
		if (status == WIN) {
			return proposed[i];
		} else {
			return proposed[1 - i];
		}
	}
}
