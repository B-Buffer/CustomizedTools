package com.customized.tools.tda;

import com.customized.tools.model.exception.ToolsException;

public class TDAException extends ToolsException {

	private static final long serialVersionUID = -3548170138130041806L;
	
	public TDAException( String msg) {
		super("CST-TDA", msg);
	}

	public TDAException( String msg, Throwable t) {
		super("CST-TDA", msg, t);
	}

}
