package com.customized.tools.cli.test;

public class A {

	protected void sayHello() {
		System.out.println("Hello World");
	}
	
	protected String echo(String msg) {
		return "echo: " + msg ;
	}
	
	private void testPrivate() {
		System.out.println("private");
	}
	
	public void testPublic() {
		System.out.println("public");
		testPrivate();
	}
}
