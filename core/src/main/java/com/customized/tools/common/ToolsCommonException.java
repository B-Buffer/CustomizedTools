package com.customized.tools.common;

import com.customized.tools.model.exception.ToolsException;

public class ToolsCommonException extends ToolsException {

	private static final long serialVersionUID = -5998204692296984310L;

	public ToolsCommonException(String msg) {
		super("CST-COM", msg);
	}
	
	public ToolsCommonException(String msg, Exception e) {
		super("CST-COM", msg, e);
	}

}
