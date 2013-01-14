package com.customized.tools.core;

public interface LifeCycle {
	
	public abstract void init();

	public abstract void start();
	
	public abstract void stop();
	
	public abstract void status();
	
	public abstract void destory();
	
}
