package com.customized.tools.smartanalyser;

import org.apache.log4j.Logger;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class GCLogAnalyser extends Analyser {
	
	private static final Logger logger = Logger.getLogger(GCLogAnalyser.class);

	public GCLogAnalyser(int status, ToolsProperties props, ToolsConsole console) {
		super(status, props, console);
	}

	public void analyser() throws Throwable {
		
		logger.info("Start GC log analysing");
	}

}
