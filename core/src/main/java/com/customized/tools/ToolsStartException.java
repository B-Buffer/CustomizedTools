package com.customized.tools;

import com.customized.tools.model.exception.ToolsException;

public class ToolsStartException extends ToolsException {

	private static final long serialVersionUID = 1254143617547688287L;

	public ToolsStartException(String msg) {
		super("CTS-START", msg);
	}
	
	public ToolsStartException(String msg, Exception e) {
		super("CTS-START", msg, e);
	}

}
