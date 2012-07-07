package com.customized.tools.dbconntest;

import com.customized.tools.startup.ToolsException;

public class DBConnectionTesterException extends ToolsException {

	private static final long serialVersionUID = 1L;
	
	
	public DBConnectionTesterException(String msg, Throwable t){
		super("CTS-DCT",msg, t);
	}

}
