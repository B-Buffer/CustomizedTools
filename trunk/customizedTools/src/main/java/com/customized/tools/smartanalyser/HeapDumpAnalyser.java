package com.customized.tools.smartanalyser;

import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class HeapDumpAnalyser extends Analyser {
	
	private static final Logger logger = Logger.getLogger(HeapDumpAnalyser.class);

	public HeapDumpAnalyser(int status, ToolsProperties props, ToolsConsole console, Set<String> imgSet) {
		super(status, props, console, imgSet);
	}

	public void analyser() throws Throwable {
		
		logger.info("Start head dump analysing");
	}

}
