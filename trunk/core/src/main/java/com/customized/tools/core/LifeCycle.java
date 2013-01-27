package com.customized.tools.core;

public interface LifeCycle {
	
	public abstract void doInit();

	public abstract void doStart();
	
	public abstract void doStop();
	
	public abstract void status();
	
	public abstract void destory();
	
}
