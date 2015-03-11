package com.customized.tools.common;

import com.customized.tools.po.exception.ToolsException;

public class ConfigurationLoaderException extends ToolsException {

	private static final long serialVersionUID = -7068830238956390231L;
	
	public ConfigurationLoaderException(String msg, Throwable t) {
		super("CST-COM", msg, t);
	}

}
