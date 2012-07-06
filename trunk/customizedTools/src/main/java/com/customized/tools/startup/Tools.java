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
	
	protected static final String TOOL_LOGGER_FILE = "Tool-log4j.xml";
	
	protected static final String TOOL_BASE = "lib";
	
	protected static final String TOOL_CONF_FILE = "lib/conf/tools.properties";
	
	protected static final String DEPENDENCY_JAR_FOLDER ="lib/jars";
	
//	protected static final String STARTUP_JAR_NAME = "lib/lib.jar";
	
	protected static final String TOOL_CORE_FILE = "Tools.xml";
	
		
	static{
		// validate migration log4j configuration file exist
		if(!(new File(TOOL_LOGGER_FILE).exists())) {
			throw new RuntimeException(new File(TOOL_LOGGER_FILE).getAbsolutePath() + " doesn't exists");
		}
		
		if(!(new File(TOOL_CONF_FILE).exists())) {
			throw new RuntimeException(new File(TOOL_CONF_FILE).getAbsolutePath() + " doesn't exists");
		}
		
		// validate migration dependency file exist
		if(!(new File(DEPENDENCY_JAR_FOLDER).exists())) {
			throw new RuntimeException(new File(DEPENDENCY_JAR_FOLDER).getAbsolutePath() + " doesn't exists");
		}
		
		// validate migration start up file exist
//		if(!(new File(STARTUP_JAR_NAME).exists())) {
//			throw new RuntimeException(new File(STARTUP_JAR_NAME).getAbsolutePath() + " doesn't exists");
//		}
		
		// validate migration core configuration file exist
		if(!(new File(TOOL_CORE_FILE).exists())) {
			throw new RuntimeException(new File(TOOL_CORE_FILE).getAbsolutePath() + " doesn't exists");
		}
		
		// configure migration log4j
		DOMConfigurator.configure(TOOL_LOGGER_FILE);
	}
	
	public static void main(String[] args) {
		try {
//			argsValidation(args);
			displayDebugInfo();
			start(TOOL_CORE_FILE, TOOL_CONF_FILE);
		} catch (Throwable t) {
			logger.error("IPCMigration Start Error", t);
		}
	}

	private static void argsValidation(String[] args) {
		if(args.length < 1) {
			System.out.println("\n\nUSAGE: "+ "com.tibco.ipc.IPCMigration <Selection>\n");
			System.out.println("<-d> Default Selection, first execute migration");
			System.out.println("<-p> Prepare Selection, pre-operation of migration, optional");
			System.out.println("<-r> Recover Selection, if the migration cursh you want to migrate continuely");
			System.out.println("<-c> Clean Selection, clean the log item when migration complete");
			System.exit(0);
		}	
		if(args[0].equals("-d") || args[0].equals("-p") || args[0].equals("-r")  || args[0].equals("-c")){
			System.out.println();
		} else {
			throw new RuntimeException("Augument paramters invalid");
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
		
//		ipcl.loadStartUpJar(STARTUP_JAR_NAME);
		
		ipcl.loadDependencyJars(DEPENDENCY_JAR_FOLDER);
		
		Thread.currentThread().setContextClassLoader(ipcl);
		
		logger.info("Tools Classloader load class successfully");
		
		ipcl.logClasspath(props);
		
		return ipcl;
	}
	
	private static void displayDebugInfo() {
		
		 if (logger.isDebugEnabled()) {
			 	logger.debug("");
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
