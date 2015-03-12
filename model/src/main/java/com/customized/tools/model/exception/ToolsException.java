package com.customized.tools.model.exception;

public class ToolsException extends RuntimeException {

	private static final long serialVersionUID = -5243883308591516193L;

	public String errorCode;

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public ToolsException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public ToolsException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public ToolsException(String errorCode, String msg, Throwable t) {
		super(msg, t);
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		Throwable cause = getCause();
		String msg = "[" + getErrorCode() + "] - " + super.getMessage();
		if (null != cause && null != cause.getMessage()) {
			msg += " (" + cause.getMessage() + ")";
		}
		return msg;
	}

	
	public String getErrorCode() {
		return errorCode;
	}

}
