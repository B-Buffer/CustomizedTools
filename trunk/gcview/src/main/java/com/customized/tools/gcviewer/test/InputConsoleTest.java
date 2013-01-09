package com.customized.tools.gcviewer.test;

import com.customized.tools.cli.InputConsole;

public class InputConsoleTest {

	public static void main(String[] args) {

		InputConsole concole = new InputConsole();
		String msg = "Run GCViewer From Command Line\n" +
					 "  [1]. Yes\n" +
				     "  [2]. No";
		int a = '1';
		int b = '2';
		int result = concole.read(msg, a, b);
		concole.pauseln(result);
	}
}
