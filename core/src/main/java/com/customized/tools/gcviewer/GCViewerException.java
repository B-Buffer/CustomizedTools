package com.customized.tools.gcviewer;

import com.customized.tools.model.exception.ToolsException;

public class GCViewerException extends ToolsException {
	
	private static final long serialVersionUID = 7454777050026916475L;

	public GCViewerException( String msg) {
		super("CST-GCV", msg);
	}

	public GCViewerException( String msg, Throwable t) {
		super("CST-GCV", msg, t);
	}

}
