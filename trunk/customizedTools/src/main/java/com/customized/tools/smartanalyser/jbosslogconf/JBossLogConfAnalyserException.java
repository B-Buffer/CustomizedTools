package com.customized.tools.smartanalyser.jbosslogconf;

import com.customized.tools.smartanalyser.SmartAnalyserException;

public class JBossLogConfAnalyserException extends SmartAnalyserException {

	private static final long serialVersionUID = -3733327599663846079L;

	public JBossLogConfAnalyserException(String msg) {
		super(msg);
		super.setErrorCode(getErrorCode() + "-JBLC");
	}
	
	public JBossLogConfAnalyserException(String msg, Exception e) {
		super(msg, e);
		super.setErrorCode(getErrorCode() + "-JBLC");
	}

}
