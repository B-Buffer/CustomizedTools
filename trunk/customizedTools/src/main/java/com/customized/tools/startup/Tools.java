package com.customized.tools.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public abstract class Tools {

	private static final Logger logger = Logger.getLogger(Tools.class);
	
	private static final String TOOLS_VERSION = "CustomizedTools 'KylinSoong' 1.0.0";
	
	protected static String baseDir = null; 
	
	private static  String loggerFile = null;
	
	private static  String classloaderFile = null;
	
	private static  String toolsConf = null;
	
	private static  String jarFolder = null;
	
	private static String coreFile = null;
		
	private static String logDir = null;
	
	
	static{
		
		if(System.getProperty("cts.baseDir") != null) {
			baseDir = System.getProperty("cts.baseDir");
		} else {
			baseDir = System.getProperty("CTS_HOME");
		}
		
		if(baseDir == null) {
			throw new ToolsStartException("CTS_HOME not set correctly");
		} 
		
		loggerFile = baseDir + "/Tool-log4j.xml";
		if (!(new File(loggerFile).exists())) {
			throw new ToolsStartException(loggerFile + " doesn't exists");
		}
			
		toolsConf = baseDir + "/lib/conf/tools.properties";
		if(!(new File(toolsConf).exists())) {
			throw new ToolsStartException(toolsConf + " doesn't exists");
		}
		
		classloaderFile = baseDir + "/lib/conf/classloader.xml";
		if(!(new File(toolsConf).exists())) {
			throw new ToolsStartException(classloaderFile + " doesn't exists");
		}
				
		jarFolder = baseDir + "/lib/jars";
		if(!(new File(jarFolder).exists())) {
			throw new ToolsStartException(jarFolder + " doesn't exists");
		}
		
		coreFile = baseDir + "/Tools.xml";
		if(!(new File(coreFile).exists())) {
			throw new ToolsStartException(coreFile + " doesn't exists");
		}
		
		logDir = baseDir + "/log";
		if(!(new File(logDir).exists())) {
			new File(logDir).mkdir();
		}
		
		System.setProperty("cts.log.dir", logDir);
		// configure migration log4j
		DOMConfigurator.configure(loggerFile);
	}
	
	public static void main(String[] args) {
		try {
			displayDebugInfo();
			start(coreFile, toolsConf);
		} catch (Throwable t) {
			logger.error("IPCMigration Start Error", t);
		}
	}

	private static void start(String path, String conf) throws Throwable {
		
		logger.info("Tools Start");
		
		ToolsProperties props = loadProperties(path);
		
		ToolsClassLoader classloader = getClassLoader(props);
				
		Tools tool = classloader.getTools(props, conf);
		
		tool.execute();
	}
	
	private static void loadClassloaderXMl(ToolsProperties props) throws Exception {
		
		loadFromFile(props, classloaderFile);
	}

	protected static ToolsProperties loadProperties(String path) throws Exception {	
		
		ToolsProperties props = new ToolsProperties();
		
		loadFromFile(props, path);
		
		loadClassloaderXMl(props);
		
		props.debugPropsInfo();
		
		return props;
	}
	
	private static void loadFromFile(Properties props, String path) throws Exception {

		File f = new File(path);
		InputStream in = null;
		try {
			in = new FileInputStream(f);
			
			props.loadFromXML(in);
		} catch (Exception e) {
			throw e;
		}finally {
			if(in != null) {
				in.close();
			}
		}
		
		logger.info("Property file [" + path + "] loaded successfully!");
	}

	protected static ToolsClassLoader getClassLoader(ToolsProperties props) throws Throwable {

		logger.info("Create Tools Classloader");
		
		ToolsClassLoader ipcl = new ToolsClassLoader();
				
		ipcl.loadDependencyJars(jarFolder);
		
		for(String folder : props.splitProperty("external.classloader.path", ";", false)){
			logger.info("Load external jars " + folder);
			if(new File(folder).exists() && new File(folder).isDirectory()){
				ipcl.loadDependencyJars(folder);
			}
		}
		
		for(String path : props.splitProperty("external.classloader.addr", ";", false)){
			logger.info("Load external jars " + path);
			ipcl.loadJarsThroughHttp(path);
		}
		
		Thread.currentThread().setContextClassLoader(ipcl);
		
		logger.info("Tools Classloader load class successfully");
		
		ipcl.logClasspath(props);
		
		return ipcl;
	}
	
	private static void displayDebugInfo() {
		
		logger.info(TOOLS_VERSION + " Starting");
		
		logger.debug("Customized Tools BaseDir: " + baseDir);
		
		logger.debug("Customized Tools Log4j configuration file: " + loggerFile);
		
		logger.debug("Customized Tools main configuration file: " + coreFile);
		
		logger.debug("Customized Tools properties configuration file: " + toolsConf);
		
		logger.debug("Customized Tools jar folder: " + jarFolder);
		
		logger.debug("Customized Tools log folder: " + logDir);
		
		if (logger.isDebugEnabled()) {
			logger.debug("displayJavaInfo()");
			logger.debug("  java.vendor: " + System.getProperty("java.vendor"));
			logger.debug("  java.vm.name: " + System.getProperty("java.vm.name"));
			logger.debug("  java.vm.version: " + System.getProperty("java.vm.version"));
			logger.debug("  java.version: " + System.getProperty("java.version"));
			logger.debug("");
		}
	}
	
	public abstract void execute() throws Throwable;

}
