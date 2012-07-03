package com.kylin.tools.jarclasssearcher;

public class JarClassSearcherException extends RuntimeException {

	private static final long serialVersionUID = -5713164813786309936L;
	
	private String msg;

	public JarClassSearcherException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
