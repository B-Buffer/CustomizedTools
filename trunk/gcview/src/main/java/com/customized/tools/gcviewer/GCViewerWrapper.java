package com.customized.tools.gcviewer;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.customized.tools.common.console.InputConsole;
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
		
		String msg = "Run GCViewer From Command Line\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No";
		int a = '1';
		int b = '2';
		int result = console.read(msg, a, b);
		
		try {
			if(result == a) {
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

	private void cmdline() throws IOException {

		
	}

}
