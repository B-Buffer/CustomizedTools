package com.customized.tools.smartanalyser;

import org.apache.log4j.Logger;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class JBossLogConfAnalyser extends Analyser {
	
	private final static Logger logger = Logger.getLogger(JBossLogConfAnalyser.class);

	public JBossLogConfAnalyser(int status, ToolsProperties props, ToolsConsole console) {
		super(status, props, console);
	}

	public void analyser() throws Throwable {

		logger.info("Start JBoss Log fies, configuration files analysing");
	}

}
