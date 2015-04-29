package com.customized.tools;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.customized.tools.model.version.Version;

public class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class);
	
	final static String CONFIGURATION = "configuration";
	final static String INPUT = "input";
	final static String LOG = "log";
	final static String OUT = "out";
	
	final static String TOOLS_LOG4J = "Tools-log4j.xml";
	final static String CORE_CONF = "CustomizedToolsContext.xml";
	
	static String baseDir = null;
	static String logFile = null;
	static String toolsConf = null;
	static String inputDir = null;
	static String outDir = null;
	static String logDir = null;
	
	static String baseDirProps = "cst.home";
	static String inputDirProps = "cst.linput.dir";
	static String outDirProps = "cst.out.dir";
	static String logDirProps = "cst.log.dir";
	
	static {
		if(System.getProperty("cst.home") != null) {
			baseDir = System.getProperty("cst.home");
		} else {
			baseDir = System.getProperty("CST_HOME");
		}
		
		if(baseDir == null) {
			throw new ToolsStartException("CST_HOME not set");
		}
		
		logFile = baseDir + File.separator + CONFIGURATION + File.separator + TOOLS_LOG4J;
		if (!(new File(logFile).exists())) {
			throw new ToolsStartException(logFile + " doesn't exists");
		}
		
		toolsConf = baseDir + File.separator + CONFIGURATION + File.separator + CORE_CONF;
		if (!(new File(toolsConf).exists())) {
			throw new ToolsStartException(toolsConf + " doesn't exists");
		}
		
		outDir = baseDir + File.separator + OUT;
		if (!(new File(outDir).exists())) {
			new File(outDir).mkdir();
		}
		
		inputDir = baseDir + File.separator + INPUT;
		if (!(new File(inputDir).exists())) {
			throw new ToolsStartException(inputDir + " doesn't exists");
		}
		
		logDir = baseDir + File.separator + LOG;
		if(!(new File(logDir).exists())) {
			new File(logDir).mkdir();
		}
		
		System.setProperty(baseDirProps, baseDir);
		System.setProperty(inputDirProps, inputDir);
		System.setProperty(outDirProps, outDir);
		System.setProperty(logDirProps, logDir);
		
		DOMConfigurator.configure(logFile);
	}
	
	static void argsValidation(String[] args) {
	}
	
	static void displayDebugInfo() {
		
		if(!logger.isDebugEnabled()) {
			return ;
		}
		
		logger.debug("Display Customized Tools Information");
		logger.debug("  Customized Tools BaseDir: " + baseDir);
		logger.debug("  Customized Tools Log4j configuration file: " + logFile);
		logger.debug("  Customized Tools main configuration file: " + toolsConf);
		logger.debug("  Customized Tools log folder: " + logDir);
		logger.debug("  Customized Tools out folder: " + outDir);
		logger.debug("  Customized Tools input folder: " + inputDir);
		
		logger.debug("Display Java Information");
		logger.debug("  java.vendor: " + System.getProperty("java.vendor"));
		logger.debug("  java.vm.name: " + System.getProperty("java.vm.name"));
		logger.debug("  java.vm.version: " + System.getProperty("java.vm.version"));
		logger.debug("  java.version: " + System.getProperty("java.version"));
		logger.debug("  java.home: " + System.getProperty("java.home"));

		logger.debug("Display OS Information");
		logger.debug("  os.name: " + System.getProperty("os.name"));
		logger.debug("  os.version: " + System.getProperty("os.version"));
		logger.debug("  user.name: " + System.getProperty("user.name"));
		logger.debug("  user.home: " + System.getProperty("user.home"));
		logger.debug("  user.language: " + System.getProperty("user.language"));
	}
	

	public static void main(String[] args) {

		argsValidation(args);
		displayDebugInfo();
		start();
	}

	static void start() {
		
		logger.info(Version.versionString() + " Start");
		
		AeshContainer container = new AeshContainer();

		container.doInit();
		
		container.doStart();
	}


}
