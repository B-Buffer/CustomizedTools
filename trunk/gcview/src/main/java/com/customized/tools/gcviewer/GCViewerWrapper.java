package com.customized.tools.gcviewer;

import org.apache.log4j.Logger;

import com.customized.tools.common.console.Console;
import com.customized.tools.gcviewer.GCViewerException;
import com.tagtraum.perf.gcviewer.GCViewer;

public class GCViewerWrapper  {
	
	private static final Logger logger = Logger.getLogger(GCViewerWrapper.class);
	
	private Console console;

	public GCViewerWrapper(Console console) {
		this.console = console ;
	}

	public void execute() {
		
		logger.info("GCViewWrapper start GCViewer");
		
		console.prompt("GCViewWrapper start GCViewer");
		
		try {
			new GCViewer();
		} catch (Exception e) {
			
			GCViewerException ex = new GCViewerException("", e);
			
			console.prompt("GCViewer Return a Error, " + ex.getMessage());
			
			logger.error(ex);
		}
	}

}
