package com.customized.tools;

import org.apache.log4j.Logger;

import com.customized.tools.smartanalyser.Constants;
import com.customized.tools.smartanalyser.GCLogAnalyser;
import com.customized.tools.smartanalyser.HeapDumpAnalyser;
import com.customized.tools.smartanalyser.IAnalyser;
import com.customized.tools.smartanalyser.JBossLogConfAnalyser;
import com.customized.tools.smartanalyser.SmartAnalyserException;
import com.customized.tools.smartanalyser.ThreadDumpAnalyser;
import com.customized.tools.smartanalyser.status.SmartAnalyserStatusException;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class SmartAnalyser extends AbstractTools {
	
	private static final Logger logger = Logger.getLogger(SmartAnalyser.class);

	public SmartAnalyser(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}
	

	public void execute() throws Throwable {
		
		logger.info("Start SmartAnalyser...");
		
		int status = getAnalyerStatus();
		
		IAnalyser analyser = null;
		
		try {
			switch (status) {
			case 1:
				analyser = new JBossLogConfAnalyser(1, props, console);
				break;
			case 2:
				analyser = new GCLogAnalyser(2, props, console);
				break;
			case 3:
				analyser = new ThreadDumpAnalyser(3, props, console);
				break;
			case 4:
				analyser = new HeapDumpAnalyser(4, props, console);
				break;
			default:
				break;
			}
			
			if(analyser != null) {
				analyser.analyser();
			} else {
				throw new SmartAnalyserStatusException("get Analyer Status return a error status code");
			}
			
		} catch (Exception e) {

			SmartAnalyserException ex = new SmartAnalyserException("Analyser returned a unexpected Exception ", e);
			
			console.prompt(ex.getMessage());
			
			throw ex;
		}
	}

	private int getAnalyerStatus() {
		
		
		return Constants.STATUS_JBOSS_LOG_CONF;
	}

}
