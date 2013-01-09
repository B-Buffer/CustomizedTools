package com.customized.tools.gcviewer;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.gcviewer.GCViewerException;
import com.tagtraum.perf.gcviewer.GCViewer;

public class GCViewerWrapper  {
	
	private static final Logger logger = Logger.getLogger(GCViewerWrapper.class);
	
	private InputConsole console;

	public GCViewerWrapper(InputConsole console) {
		this.console = console ;
	}

	public void execute() {
		
		logger.info("GCViewWrapper start GCViewer");
		
		console.prompt("GCViewWrapper start GCViewer");
			
		try {
			if(console.readFromCli("GCViewer")) {
				String[] args = new String[2];
				args[0] = console.readFilePath("Input gc log path [<gc-log-file>]", true);
				args[1] = console.readFilePath("Input result save file [<export.csv>]", false);
				new GCViewer().main(args);
				console.prompt("Please check result from " + args[1]);
			} else {
				new GCViewer().main(new String[0]);
			}
		} catch (Exception e) {
			GCViewerException ex = new GCViewerException("", e);
			console.prompt("GCViewer Return a Error, " + ex.getMessage());
			logger.error("", ex);
			throw ex;
		}
	}

}
