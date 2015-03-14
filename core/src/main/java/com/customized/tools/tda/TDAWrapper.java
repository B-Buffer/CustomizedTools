package com.customized.tools.tda;

import org.apache.log4j.Logger;

import com.customized.tools.ITool;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.Entity;
import com.customized.tools.model.TDAEntity;
import com.pironet.tda.TDA;

public class TDAWrapper implements ITool {
	
	private static final Logger logger = Logger.getLogger(TDAWrapper.class);
	
	private InputConsole console;
	
	private TDAEntity tda;
	
	
	public TDAWrapper(InputConsole console, Entity entity) {
		this.console = console;
		this.tda = (TDAEntity) entity;
	}

	@Override
	public void execute() {
		
		logger.info("TDAWrapper start TDA");
		
		console.prompt("TDAWrapper start TDA");
		
		try {
			if(console.readFromCli("TDA")) {
				String[] args = new String[1];
				args[0] = console.readFilePath("Input thread dump file path", tda.getPath(), true);
				TDA.main(args);
			} else {
				TDA.main(new String[0]);
			}
		} catch (Throwable e) {
			TDAException ex = new TDAException("TDA run return a exception", e);
			console.prompt("TDA Return a Error, " + ex.getMessage());
			logger.error("", ex);
//			throw ex;
		}
		
	}

}
