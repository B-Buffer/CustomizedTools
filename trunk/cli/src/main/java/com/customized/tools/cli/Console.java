package com.customized.tools.cli;

public class Console {

	private static final long DEFAULT_SLEEP_TIME = 1000;
	
	protected static final String TAB = "    ";
	
	public void print(Object obj) {
		System.out.print(obj);
	}

	public void println(Object obj) {
		System.out.println(obj);
	}
	
	public void prompt(Object obj) {
		System.out.println("\n" + TAB + obj + "\n");
	}

	public void pause(Object obj) {
		System.out.print(obj);
		sleep(DEFAULT_SLEEP_TIME);
	}

	public void pauseln(Object obj) {
		System.out.println(obj);
		sleep(DEFAULT_SLEEP_TIME);
	}

	@SuppressWarnings("static-access")
	public void sleep(long time) {
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException("Thread [" + Thread.currentThread().getName() + "] was Interrupted", e);
		}
	}
	
	protected abstract class Validation {
		
		protected abstract boolean validate(Object obj) throws RuntimeException ;
	}
}
