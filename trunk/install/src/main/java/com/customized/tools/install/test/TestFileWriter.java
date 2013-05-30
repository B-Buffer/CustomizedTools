package com.customized.tools.install.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestFileWriter {
	
	final static String STR_SIGN = "<$#%&54#%%^&>";

	public static void main(String[] args) throws IOException {

		String path = "/home/kylin/work/project/CustomizedTools/trunk/build/CustomizedTools/modules/db-driver/main/module.xml";
		

		StringBuffer sb = new StringBuffer();
		sb.append("<resources>\n");
		sb.append("    <resource-root path=test.jar/>\n");
		sb.append("</resources>");
		
		StringBuffer buffer = new StringBuffer();
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "", oldtext = "";
		while ((line = reader.readLine()) != null) {
			if(line.contains(STR_SIGN)) {
				line = line.replaceAll(STR_SIGN, sb.toString());
			}
			buffer.append(line + "\n\n");
		}
		reader.close();
		
		System.out.println(buffer.toString());
	}

}
