package com.customized.tools.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ToolsURLClassLoader extends URLClassLoader {
	
	private static final Logger logger = Logger.getLogger(ToolsURLClassLoader.class);

	public ToolsURLClassLoader(ClassLoader parent) {
		super(new URL[] { }, parent);
	}
	
	public void loadDependencyJars(String folder) {

        logger.info("Load CustomizedTools dependency jars to classpath from " + folder);

        logger.debug("Recursing into " + folder);

        List<URL>list = new ArrayList<URL>();

        try {
			File file = new File(folder);
			File[] sub = file.listFiles();
			for (int i = 0; i < sub.length; i++) {

			        if(logger.isDebugEnabled()){
			                logger.debug("Examining file: " + sub[i]);
			        }

			        if (!sub[i].isDirectory() && sub[i].getAbsolutePath().endsWith(".jar")) {
			                list.add(sub[i].toURL());
			        }
			}
		} catch (MalformedURLException e) {
			throw new ResourceLoaderException("load " + folder + " jar error", e);
		}

		addURLs(list);
	}

	private void addURLs(List<URL> urls) {
		
		final int size = urls.size();
		
		for (int i = 0; i < size; i++) {
			// ensure that urls does not contain spaces
			String s = urls.get(i).toString().replaceAll(" ", "%20");

			if (logger.isDebugEnabled()) {
				logger.debug("Adding to path: " + s);
			}

			try {
				addURL(new URL(s));
			} catch (MalformedURLException e) {
				throw new RuntimeException(s + " url is not a well formed url");
			}

		}

	}

}
