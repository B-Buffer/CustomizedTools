package com.customized.tools.test.cst06;

public class ModularClassLoadingTest {

	public static void main(String[] args) throws Throwable {

		String[] array = new String[] {
				"-mp",
				"/home/kylin/work/project/CustomizedTools/trunk/build/CustomizedTools/modules",
				"bootstrap.test.cst06" };
		
		org.jboss.modules.Main.main(array);
		
	}

}
