package com.customized.tools.smartanalyser;

import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class JBossLogConfAnalyser extends Analyser {
	
	private final static Logger logger = Logger.getLogger(JBossLogConfAnalyser.class);

	public JBossLogConfAnalyser(int status, ToolsProperties props, ToolsConsole console, Set<String> imgSet) {
		super(status, props, console, imgSet);
	}

	public void analyser() throws Throwable {

		logger.info("Start JBoss Log fies, configuration files analysing");
		
		console.prompt("\n  JBossLogConfAnalyser start...\n");
		
		for(String str : imgSet) {
			System.out.println(str);
		}
	}

}
