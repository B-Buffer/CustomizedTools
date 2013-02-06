package com.customized.tools.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ResourceLoader {
	
	static Set<File> baseDir = new HashSet<File>();

	private static ResourceLoader instance;

	private ResourceLoader() {
		registerBaseDir(new File("").getAbsolutePath());
	}

	public static ResourceLoader getInstance() {

		if (null == instance)
			instance = new ResourceLoader();
		
		return instance;
	}
	
	public static void registerBaseDir(String dir) {
		
		if(!new File(dir).exists() || !new File(dir).isDirectory()) {
			throw new IllegalArgumentException(dir + " should be a directory");
		}
		
		baseDir.add(new File(dir));
	}
	
	public InputStream getResourceAsStream(String name) {
		
		InputStream in = null ;
		
		for(File file : baseDir) {
			try {
				in = new FileInputStream(new File(file, name));
				return in ;
			} catch (FileNotFoundException e) {
			}
		}
		
		if(null == in)
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		
		if(null == in)
			throw new ResourceLoaderException("can not load " + name + " as stream");
		
		return in ;
	}
	
	public File getResourceAsFile (String name) {
	
		for(File file : baseDir) {
			if(new File(file, name).exists()) {
				return new File(file, name) ;
			}
		}
		
		throw new ResourceLoaderException("Can not find " + name);
	}
}
