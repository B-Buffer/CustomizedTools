package com.customized.tools.smartanalyser;

import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Analyser;

public class JBossLogConfAnalyser extends AbstractAnalyser {
	
	private final static Logger logger = Logger.getLogger(JBossLogConfAnalyser.class);

	protected Analyser analyser;
	
	protected InputConsole console;
	
	public JBossLogConfAnalyser(int status, Analyser analyser, InputConsole console, Set<String> imgSet) {
		super(status, analyser, console, imgSet);
	}

	public void analyser() throws Throwable {

		logger.info("Start JBoss Log fies, configuration files analysing");
		
		console.prompt("\n  JBossLogConfAnalyser start...\n");
		
		for(String str : imgSet) {
			System.out.println(str);
		}
	}

}
