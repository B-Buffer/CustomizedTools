package com.customized.tools.smartanalyser;

import org.apache.log4j.Logger;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class ThreadDumpAnalyser extends Analyser {
	
	private static final Logger logger = Logger.getLogger(ThreadDumpAnalyser.class);

	public ThreadDumpAnalyser(int status, ToolsProperties props,
			ToolsConsole console) {
		super(status, props, console);
	}

	public void analyser() throws Throwable {

		logger.info("Start thread dump analysing");
	}

}
