package com.customized.tools.smartanalyser;

import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Analyser;

public class HeapDumpAnalyser extends AbstractAnalyser {
	
	private static final Logger logger = Logger.getLogger(HeapDumpAnalyser.class);

	protected Analyser analyser;
	
	protected InputConsole console;
	
	public HeapDumpAnalyser(int status, Analyser analyser, InputConsole console, Set<String> imgSet) {
		super(status, analyser, console, imgSet);
	}

	public void analyser() throws Throwable {
		
		logger.info("Start head dump analysing");
	}

}
