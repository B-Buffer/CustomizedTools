package com.customized.tools.cli.test;

import com.customized.tools.cli.InputConsole;

/**
 * Demo for using the InputConsole
 * 
 * @author kylin
 *
 */
public class InputConsoleTest {

	public static void main(String[] args) {

		InputConsole console = new InputConsole();
		long res = console.readLong("Input Value", 100);
		console.println(res);
	}

}
