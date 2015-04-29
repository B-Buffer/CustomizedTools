package com.customized.tools.tda;

import org.apache.log4j.Logger;

import com.customized.tools.AbstractTools;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.Entity;
import com.customized.tools.model.TDAEntity;
import com.pironet.tda.TDA;

public class TDAWrapper extends AbstractTools {
	
	private static final Logger logger = Logger.getLogger(TDAWrapper.class);
		
	private TDAEntity tda;
	
	public TDAWrapper(Entity entity, InputConsole console) {
		super(entity, console);
	}
	
	public TDAWrapper(InputConsole console, boolean isAesh) {
		super(null, console, isAesh);
	}

	@Override
	public void execute() {
		
		tda = (TDAEntity) entity;
		
		logger.info("TDAWrapper start TDA");
		
		console.prompt("TDAWrapper start TDA");
		
		try {
			if(isAesh) {
				String[] args = new String[1];
				args[0] = tda.getPath();
				TDA.main(args);
			} else if(console.readNotFromCli("TDA")) {
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
