package com.customized.tools.smartanalyser;

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
	
	protected ToolsProperties props;
	
	protected ToolsConsole console;

	public Analyser(int status, ToolsProperties props, ToolsConsole console) {
		super();
		this.status = status;
		this.props = props;
		this.console = console;
	}
}
