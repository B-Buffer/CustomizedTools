package com.customized.tools.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public abstract class Tools {

	private static final Logger logger = Logger.getLogger(Tools.class);
	
	private static final String TOOLS_VERSION = "CustomizedTools 'KylinSoong' 1.0.0";
	
	protected static String baseDir = null; 
	
	protected static  String loggerFile = null;
	
	protected static  String TOOL_BASE = null;
	
	protected static  String toolsConf = null;
	
	protected static  String jarFolder = null;
	
	protected static String coreFile = null;
		
	protected static String logDir = null;
	
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
			try {
				new File(logDir).createNewFile();
			} catch (IOException e) {
				throw new ToolsStartException("create " + logDir + " error", e);
			}
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
				
		Tools tool = classloader.getMigration(props, conf);
		
		tool.execute();
	}
	
	protected static ToolsProperties loadProperties(String path) throws InvalidPropertiesFormatException, FileNotFoundException, IOException {	
		
		ToolsProperties props = new ToolsProperties();
		
		File f = new File(path);
		props.loadFromXML(new FileInputStream(f));
			
		logger.info("Property file [" + f.getAbsolutePath() + "] loaded successfully!");
		
		props.debugPropsInfo();
		
		return props;
	}
	
	protected static ToolsClassLoader getClassLoader(ToolsProperties props) throws Throwable {

		logger.info("Create Tools Classloader");
		
		ToolsClassLoader ipcl = new ToolsClassLoader();
				
		ipcl.loadDependencyJars(jarFolder);
		
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
