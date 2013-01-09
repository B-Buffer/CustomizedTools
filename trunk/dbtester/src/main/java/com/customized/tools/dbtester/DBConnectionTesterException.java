package com.customized.tools.dbtester;

import com.customized.tools.po.exception.ToolsException;

public class DBConnectionTesterException extends ToolsException {

	private static final long serialVersionUID = 1L;
	
	
	public DBConnectionTesterException(String msg, Throwable t){
		super("CTS-DCT",msg, t);
	}

}
