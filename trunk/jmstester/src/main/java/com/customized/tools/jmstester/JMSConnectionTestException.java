package com.customized.tools.jmstester;

import com.customized.tools.po.exception.ToolsException;

public class JMSConnectionTestException extends ToolsException {

	
	private static final long serialVersionUID = 8834081373334653303L;
	
	public JMSConnectionTestException( String msg) {
		super("CTS-JCT", msg);
	}

	public JMSConnectionTestException( String msg, Throwable t) {
		super("CTS-JCT", msg, t);
	}

}
