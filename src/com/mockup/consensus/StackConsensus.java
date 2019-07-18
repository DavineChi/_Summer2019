package com.mockup.consensus;

import java.util.Stack;

public class StackConsensus<T> extends ConsensusProtocol<T> {
	private Stack<T> stack;
	public StackConsensus() {
		stack = new Stack<T>();
	}
	@Override
	public T decide(T value) {  // determine which thread went first
		propose(value);
		T item = stack.pop();
		int i = (int)Thread.currentThread().getId();
		if (item == value) {
			return proposed[i];
		} else {
			return proposed[1 - i];
		}
	}
	public void push(T x) {
		stack.push(x);
	}
	public T pop() {
		return decide(stack.pop());
	}
}
