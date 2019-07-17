package com.mockup;

public class UsefulWidget {
	private boolean value = false;
	public void flip() {
		value = !value;
	}
	public boolean get() {
		return value;
	}
}
