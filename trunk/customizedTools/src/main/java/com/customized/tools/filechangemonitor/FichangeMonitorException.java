package com.customized.tools.filechangemonitor;

import com.customized.tools.startup.ToolsException;

public class FichangeMonitorException extends ToolsException {
	
	private static final long serialVersionUID = 1239375507505344584L;

	public FichangeMonitorException(String msg) {
		super("CTS-FCM", msg);
	}

}
