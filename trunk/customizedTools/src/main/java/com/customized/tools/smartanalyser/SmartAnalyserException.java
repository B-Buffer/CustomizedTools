package com.customized.tools.smartanalyser;

import com.customized.tools.startup.ToolsException;

public class SmartAnalyserException extends ToolsException {

	private static final long serialVersionUID = 5495729610606925388L;

	public SmartAnalyserException(String msg) {
		super("CTS-SA", msg);
	}
	
	public SmartAnalyserException(String msg, Exception e) {
		super("CTS-SA", msg, e);
	}

}
