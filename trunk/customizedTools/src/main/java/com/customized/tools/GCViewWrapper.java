package com.customized.tools;

import org.apache.log4j.Logger;

import com.customized.tools.gcviewer.GCViewerException;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;
import com.tagtraum.perf.gcviewer.GCViewer;

public class GCViewWrapper extends AbstractTools {
	
	private static final Logger logger = Logger.getLogger(GCViewWrapper.class);

	public GCViewWrapper(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
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
