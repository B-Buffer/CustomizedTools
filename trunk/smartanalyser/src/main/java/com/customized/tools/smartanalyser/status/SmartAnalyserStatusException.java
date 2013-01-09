package com.customized.tools.smartanalyser.status;

import com.customized.tools.smartanalyser.SmartAnalyserException;

public class SmartAnalyserStatusException extends SmartAnalyserException {

	private static final long serialVersionUID = -3010417147439943925L;

	public SmartAnalyserStatusException(String msg) {
		super(msg);
		super.setErrorCode(getErrorCode() + "-STA");
	}
	
	public SmartAnalyserStatusException(String msg, Exception e) {
		super(msg, e);
		super.setErrorCode(getErrorCode() + "-STA");
	}

}
