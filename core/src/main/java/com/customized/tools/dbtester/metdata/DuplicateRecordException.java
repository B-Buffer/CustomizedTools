package com.customized.tools.dbtester.metdata;

public class DuplicateRecordException extends RuntimeException {

	private static final long serialVersionUID = -2742754559689893455L;

	public DuplicateRecordException() {
		super();
	}

	public DuplicateRecordException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateRecordException(String message) {
		super(message);
	}

	public DuplicateRecordException(Throwable cause) {
		super(cause);
	}

}
