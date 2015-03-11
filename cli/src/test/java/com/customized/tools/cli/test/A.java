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
	
	public static void main(String[] args) {
		String path = "/" ;
		
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		
		if(path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		
		String[] array = path.split("/");
		for(int i = 0 ; i < array.length ; i ++) {
			System.out.println(i + " - " + array[i]);
		}
	}
}
