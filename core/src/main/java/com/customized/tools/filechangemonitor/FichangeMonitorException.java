package com.customized.tools.filechangemonitor;

import com.customized.tools.po.exception.ToolsException;

public class FichangeMonitorException extends ToolsException {
	
	private static final long serialVersionUID = 1239375507505344584L;

	public FichangeMonitorException(String msg) {
		super("CTS-FCM", msg);
	}
	
	public FichangeMonitorException(String msg, Exception e) {
		super("CTS-FCM", msg, e);
	}

}
