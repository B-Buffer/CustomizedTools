package com.customized.tools.cli.test;

import com.customized.tools.cli.InputConsole;


public class InputConsoleTest {

	public static void main(String[] args) {

		InputConsole console = new InputConsole();
		int res = console.keyPress("Read Enter From Console");
		console.pauseln(res);
	}

}
