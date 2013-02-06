package com.customized.tools.common;

public class ResourceLoaderException extends RuntimeException {

	private static final long serialVersionUID = -2379390421584857409L;

	
	public ResourceLoaderException(String msg) {
		super(msg);
	}
	
	public ResourceLoaderException(String msg, Throwable t) {
		super(msg, t);
	}
}
