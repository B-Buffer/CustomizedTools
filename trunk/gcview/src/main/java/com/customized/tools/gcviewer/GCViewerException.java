package com.customized.tools.gcviewer;

import com.customized.tools.common.ToolsException;

public class GCViewerException extends ToolsException {
	
	private static final long serialVersionUID = 7454777050026916475L;

	public GCViewerException( String msg) {
		super("CTS-GCV", msg);
	}

	public GCViewerException( String msg, Throwable t) {
		super("CTS-GCV", msg, t);
	}

}
