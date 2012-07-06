package com.customized.tools;

import com.customized.tools.startup.Tools;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public abstract class AbstractTools extends Tools {
	
	protected ToolsProperties props;
	
	protected ToolsConsole console;
	
	public AbstractTools(ToolsProperties props, ToolsConsole console) {
		super();
		this.props = props;
		this.console = console;
	}

}
