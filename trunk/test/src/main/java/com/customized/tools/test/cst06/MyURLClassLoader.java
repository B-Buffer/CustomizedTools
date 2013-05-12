package com.customized.tools.test.cst06;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class MyURLClassLoader extends URLClassLoader {
	

	public MyURLClassLoader(ClassLoader parent) {
		super(new URL[] { }, parent);
	}
	
	public void loadDependencyJars(String folder) {

        List<URL>list = new ArrayList<URL>();

        try {
			File file = new File(folder);
			File[] sub = file.listFiles();
			for (int i = 0; i < sub.length; i++) {

			        if (!sub[i].isDirectory() && sub[i].getAbsolutePath().endsWith(".jar")) {
			                list.add(sub[i].toURL());
			        }
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("load " + folder + " jar error", e);
		}

		addURLs(list);
	}

	private void addURLs(List<URL> urls) {
		
		final int size = urls.size();
		
		for (int i = 0; i < size; i++) {
			// ensure that urls does not contain spaces
			String s = urls.get(i).toString().replaceAll(" ", "%20");

			try {
				addURL(new URL(s));
			} catch (MalformedURLException e) {
				throw new RuntimeException(s + " url is not a well formed url");
			}

		}

	}

}
