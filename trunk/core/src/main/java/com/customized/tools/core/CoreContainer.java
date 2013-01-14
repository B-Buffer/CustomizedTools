package com.customized.tools.core;

import org.apache.log4j.Logger;

import com.customized.tools.common.Configuration;


public class CoreContainer implements LifeCycle {
	
	private final static Logger logger = Logger.getLogger(CoreContainer.class);
	
	private String mainConf;
	
	private Configuration configuration;

	public CoreContainer(String mainConf) {
		this.mainConf = mainConf;
	}

	public void init() {

		configuration = new Configuration(mainConf);
	}

	public void start() {

	}

	public void stop() {

	}

	public void status() {

	}

	public void destory() {

	}

}
