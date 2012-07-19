package com.customized.tools.smartanalyser.jbosslogconf;

import org.apache.log4j.Logger;

public abstract class JBLCThreadBase {
	
	private static final Logger logger = Logger.getLogger(JBLCThreadBase.class);

	private boolean isActive = false;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void logThreadStatus(String threadName) {
		logger.info(threadName + " isActive: " + isActive());
	}

	protected Threshold threshold;
	
	public JBLCThreadBase(Threshold threshold) {
		this.threshold = threshold;
	}

	public abstract String getName();
}
