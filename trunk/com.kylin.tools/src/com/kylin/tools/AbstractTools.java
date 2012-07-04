package com.kylin.tools;

import com.kylin.tools.startup.Tools;
import com.kylin.tools.startup.ToolsConsole;
import com.kylin.tools.startup.ToolsProperties;

public abstract class AbstractTools extends Tools {
	
	protected ToolsProperties props;
	
	protected ToolsConsole console;
	
	public AbstractTools(ToolsProperties props, ToolsConsole console) {
		super();
		this.props = props;
		this.console = console;
	}

}
