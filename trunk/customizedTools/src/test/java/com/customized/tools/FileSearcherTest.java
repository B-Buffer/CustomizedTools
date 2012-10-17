package com.customized.tools;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileSearcherTest {

	public static void main(String[] args) throws IOException {

		File f = new File("Tools.xml");
		System.out.println(f.getAbsolutePath());
		
		String path = f.getAbsolutePath() ;
		
		System.out.println(path.substring(path.indexOf("CustomizedTools")));
		System.out.println(f.getName());
		
		File file = new File("/home/kylin/work/test/build/demo/demo.servlet-1.0.jar");
		ZipFile zip = new ZipFile(file);
		Enumeration <ZipEntry>en = (Enumeration<ZipEntry>) zip.entries();
		while(en.hasMoreElements()) {
			
			ZipEntry entry = en.nextElement() ;
			
			System.out.println("  " + entry.getName());
		}
		
		System.out.println("fucker\ngg");
	}

}
