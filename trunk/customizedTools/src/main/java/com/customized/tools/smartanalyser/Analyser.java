package com.customized.tools.smartanalyser;

import java.util.Set;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public abstract class Analyser implements IAnalyser {

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	protected Set<String> imgSet;
	
	protected ToolsProperties props;
	
	protected ToolsConsole console;

	public Analyser(int status, ToolsProperties props, ToolsConsole console, Set<String> imgSet) {
		super();
		this.status = status;
		this.props = props;
		this.console = console;
		this.imgSet = imgSet;
	}
}
