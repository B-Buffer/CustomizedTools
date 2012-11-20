package com.customized.tools;

import com.customized.tools.startup.Tools;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public abstract class AbstractTools extends Tools {
	
	protected ToolsProperties props;
	
	protected ToolsConsole console;
	
	protected String errProp = "Unexpected Exception Returned";
	
	public AbstractTools(ToolsProperties props, ToolsConsole console) {
		super();
		this.props = props;
		this.console = console;
	}
	
	protected String errNote(Exception e) {
		return errProp + ", " + e.getMessage() + "\n Find more error info please refer to log file";
	}

}
