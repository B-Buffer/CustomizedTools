package com.kylin.tools.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ToolsClassLoader extends URLClassLoader {
	
	private static final Logger logger = Logger.getLogger(ToolsClassLoader.class);
	
	protected static final Class[] types = { ToolsProperties.class, ToolsConsole.class};

	public ToolsClassLoader(URL u) {
		super(new URL[] { u });
	}
	
	public ToolsClassLoader() {
		super(new URL[] { });
	}

	public Tools getMigration(ToolsProperties props, String conf) throws Throwable {
		
		Map<String, String> map = loadTools(conf);
		
		ToolsConsole console = new ToolsConsole(props.getProperty("mode"));
		
		String cls = console.getToolClass(props, map);
		
		logger.info("Loading Class: " + cls);
		
		return (Tools) loadClass(cls).getConstructor(types).newInstance(new Object[] { props, console});
	}
	
	private Map loadTools(String conf) throws IOException {
		
		InputStream in = new FileInputStream(new File(conf));
		
		Properties prop = new Properties();
		
		prop.load(in);
		
		in.close();
		
		return prop;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String, String> map = new ToolsClassLoader().loadTools("lib/conf/tools.properties");
		System.out.println(map);
	}

	protected void loadStartUpJar(String name) throws MalformedURLException {
		
		logger.info("load migration start up jar to classpath");
		
		List<URL>list = new ArrayList<URL>();
		
		File file = new File(name);
		
		if(logger.isDebugEnabled()){
			logger.debug("Examining file: " + file);
		}
		
		if (!file.isDirectory() && file.getAbsolutePath().endsWith(".jar")) {
			list.add(file.toURL());
		}
		
		addURLs(list);
	}
	
	protected void loadDependencyJars(String folder) throws MalformedURLException {
		
		logger.info("load migration dependency jars to classpath");
		
		logger.debug("Recursing into " + folder);
		
		List<URL>list = new ArrayList<URL>();
		
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
		
		addURLs(list);
	}

	private void addURLs(List<URL>urls) {
		final int size = urls.size();
		for (int i = 0; i < size; i++) {
			// ensure that urls does not contain spaces
			String s = urls.get(i).toString().replaceAll(" ", "%20");
			
			if(logger.isDebugEnabled()){
				logger.debug("Adding to path: " + s);
			}
			
			try {
				addURL(new URL(s));
			} catch (MalformedURLException e) {
				throw new RuntimeException(s + " url is not a well formed url");
			}

		}
	}
	
	protected void logClasspath(ToolsProperties properties) {
		if(properties.getProperty("log.classpath", "false").equals("true")) {
			logger.info("Log Classpath is available, loging classpath");
			URLClassLoader loader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
			URL[] urls = loader.getURLs();
			for (int i = 0; i < urls.length; i++) {
				logger.info(urls[i].getPath());
			}
		}
	}
	
}
