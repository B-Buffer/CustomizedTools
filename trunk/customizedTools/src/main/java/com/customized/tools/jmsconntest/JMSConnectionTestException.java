package com.customized.tools.jmsconntest;

import com.customized.tools.startup.ToolsException;

public class JMSConnectionTestException extends ToolsException {

	
	private static final long serialVersionUID = 8834081373334653303L;
	
	public JMSConnectionTestException( String msg) {
		super("CTS-JCT", msg);
	}

	public JMSConnectionTestException( String msg, Throwable t) {
		super("CTS-JCT", msg, t);
	}

}
