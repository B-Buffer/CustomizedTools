package com.customized.tools.core.test;

import java.io.File;

import com.customized.tools.core.Main;

public class Runner {

	public static void main(String[] args) {
		
		System.setProperty("CST_HOME", new File("").getAbsolutePath());
		
		new Main().main(args);
	}

}
