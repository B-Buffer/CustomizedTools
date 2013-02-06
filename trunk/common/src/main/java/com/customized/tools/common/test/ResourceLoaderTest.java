package com.customized.tools.common.test;

import java.io.File;

import com.customized.tools.common.ResourceLoader;

public class ResourceLoaderTest {

	static void testFile() {
		File file = new File("");

		System.out.println(file);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.isDirectory());
		System.out.println(file.exists());
		System.out.println(new File(file, "pom.xml"));
		System.out.println(new File(file, "pom.xml").getAbsolutePath());
		System.out.println(new File(file, "pom.xml").isDirectory());
		System.out.println(new File(file, "pom.xml").exists());
		System.out.println(new File(new File(file.getAbsolutePath()), "pom.xml").isDirectory());
		System.out.println(new File(new File(file.getAbsolutePath()), "pom.xml").exists());
	}

	public static void main(String[] args) {
		ResourceLoader.getInstance();
		
		testFile();
	}

}
