package com.customized.tools.searcher;

import com.customized.tools.po.exception.ToolsException;

public class FileSearcherException extends ToolsException {

	private static final long serialVersionUID = 8094813736298659263L;

	public FileSearcherException( String msg) {
		super("CTS-FS", msg);
	}

	public FileSearcherException( String msg, Throwable t) {
		super("CTS-FS", msg, t);
	}
}
