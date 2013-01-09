package com.customized.tools.smartanalyser;

import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Analyser;

public class ThreadDumpAnalyser extends AbstractAnalyser {
	
	private static final Logger logger = Logger.getLogger(ThreadDumpAnalyser.class);

	protected Analyser analyser;
	
	protected InputConsole console;
	
	public ThreadDumpAnalyser(int status, Analyser analyser, InputConsole console, Set<String> imgSet) {
		super(status, analyser, console, imgSet);
	}

	public void analyser() throws Throwable {

		logger.info("Start thread dump analysing");
	}

}
