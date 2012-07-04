package com.kylin.tools.dbconntest;

public class DBConnectionTesterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public DBConnectionTesterException(String msg, Throwable t){
		super(msg, t);
	}

}
