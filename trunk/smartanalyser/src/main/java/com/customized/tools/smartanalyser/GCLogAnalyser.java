package com.customized.tools.smartanalyser;

import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Analyser;

public class GCLogAnalyser extends AbstractAnalyser {
	
	private static final Logger logger = Logger.getLogger(GCLogAnalyser.class);
	
	protected Analyser analyser;
	
	protected InputConsole console;

	public GCLogAnalyser(int status, Analyser analyser, InputConsole console, Set<String> imgSet) {
		super(status, analyser, console, imgSet);
	}

	public void analyser() throws Throwable {
		
		logger.info("Start GC log analysing");
	}

}
