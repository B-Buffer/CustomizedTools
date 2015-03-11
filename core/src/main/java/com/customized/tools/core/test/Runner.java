package com.customized.tools.core.test;

import java.io.File;

import com.customized.tools.Main;

public class Runner {

	public static void main(String[] args) {
		
		System.setProperty("CST_HOME", new File("").getAbsolutePath());
		
		String str = System.getProperty("CST_HOME") + File.separator + "lib";
		
		System.out.println(str);
//		new Main().main(args);
	}

}
