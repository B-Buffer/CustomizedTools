package com.customized.tools.install.test;

import com.customized.tools.install.Main;

public class Test {

	public static void main(String[] args) throws Exception {

		System.setProperty("cst.home", "/home/kylin/work/project/CustomizedTools/trunk/build/CustomizedTools");
		
		Main.main(new String[]{});
	}

}
