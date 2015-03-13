package com.customized.tools.gcviewer;

import java.io.File;

import org.apache.log4j.Logger;

import com.customized.tools.ITool;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.gcviewer.GCViewerException;
import com.customized.tools.model.Entity;
import com.customized.tools.model.GCViewerEntity;
import com.tagtraum.perf.gcviewer.GCViewer;

public class GCViewerWrapper implements ITool {
	
	private static final Logger logger = Logger.getLogger(GCViewerWrapper.class);
	
	private InputConsole console;
	private GCViewerEntity gcViwer;

	public GCViewerWrapper(InputConsole console, Entity entity) {
		this.console = console ;
		this.gcViwer = (GCViewerEntity) entity;
	}

	public void execute() {
		
		logger.info("GCViewWrapper start GCViewer");
		
		console.prompt("GCViewWrapper start GCViewer");
			
		try {
			if(console.readFromCli("GCViewer")) {
				String[] args = new String[2];
				args[0] = console.readFilePath("Input gc log file path", gcViwer.getPath(), true);
				args[1] = System.getProperty("cst.out.dir") + File.separator + console.readString("Input result save file", gcViwer.getName(), false);
				
				if( !new File(args[0]).exists()) {
					
					File inputFile = new File(System.getProperty("cst.linput.dir") + File.separator + args[0]);
					if(inputFile.exists()) {
						args[0] = inputFile.getAbsolutePath();
					} else {
						console.prompt(new GCViewerException("'" + args[0] + "' does not exist").getMessage());
						return;
					}
					
				}
				
				GCViewer.main(args);
				console.prompt("Please check result from " + args[1]);
			} else {
				GCViewer.main(new String[0]);
			}
		} catch (Exception e) {
			GCViewerException ex = new GCViewerException("", e);
			console.prompt("GCViewer Return a Error, " + ex.getMessage());
			logger.error("", ex);
//			throw ex;
		}
	}

}
