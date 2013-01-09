package com.customized.tools.jarClassSearcher;

import com.customized.tools.po.exception.ToolsException;

public class JarClassSearcherException extends ToolsException {

	private static final long serialVersionUID = -5713164813786309936L;
	
	public JarClassSearcherException(String msg) {
		super("CTS-JCS", msg);
	}
	
	public JarClassSearcherException(String msg, Exception e) {
		super("CTS-JCS", msg, e);
	}
}
