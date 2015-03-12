package com.customized.tools.searcher;

import com.customized.tools.model.exception.ToolsException;

public class FileSearcherException extends ToolsException {

	private static final long serialVersionUID = 8094813736298659263L;

	public FileSearcherException( String msg) {
		super("CST-FS", msg);
	}

	public FileSearcherException( String msg, Throwable t) {
		super("CST-FS", msg, t);
	}
}
