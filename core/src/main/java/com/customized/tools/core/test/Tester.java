package com.customized.tools.core.test;

import java.io.File;

public class Tester {

	public static void main(String[] args) {

		new Tester().enumTest(); 
	}
	
	private Status status = Status.STOP;

	private void enumTest() {

		System.out.println(status.equals(Status.STOP));
		System.out.println(status == Status.STOP);
	}
	
	enum Status {
		STOP,
		INIT,
		START,
		DESTORY,
	}

}
