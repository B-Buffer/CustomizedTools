package com.customized.tools.dbtester;

import com.customized.tools.model.exception.ToolsException;

public class DBConnectionTesterException extends ToolsException {

	private static final long serialVersionUID = 1L;
	
	
	public DBConnectionTesterException(String msg, Throwable t){
		super("CST-DCT",msg, t);
	}

}
