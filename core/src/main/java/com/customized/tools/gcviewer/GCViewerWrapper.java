package com.customized.tools.gcviewer;

import java.io.File;

import org.apache.log4j.Logger;

import com.customized.tools.AbstractTools;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.gcviewer.GCViewerException;
import com.customized.tools.model.Entity;
import com.customized.tools.model.GCViewerEntity;
import com.tagtraum.perf.gcviewer.GCViewer;

public class GCViewerWrapper extends AbstractTools {
	
	private static final Logger logger = Logger.getLogger(GCViewerWrapper.class);
	
	private GCViewerEntity gcViwer;

	public GCViewerWrapper(Entity entity, InputConsole console) {
		super(entity, console);
	}
		
	public void execute() {
		
		gcViwer = (GCViewerEntity) entity;
		
		logger.info("GCViewWrapper start GCViewer");
		
		console.prompt("GCViewWrapper start GCViewer");
			
		try {
			if(console.readNotFromCli("GCViewer")) {
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
		} catch (Throwable e) {
			GCViewerException ex = new GCViewerException("", e);
			console.prompt("GCViewer Return a Error, " + ex.getMessage());
			logger.error("", ex);
//			throw ex;
		}
	}

}
