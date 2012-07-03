package com.kylin.tools;

import com.kylin.tools.startup.Tool;
import com.kylin.tools.startup.ToolConsole;
import com.kylin.tools.startup.ToolProperties;

public abstract class AbstractTools extends Tool {
	
	protected ToolProperties props;
	
	protected ToolConsole console;
	
	public AbstractTools(ToolProperties props, ToolConsole console) {
		super();
		this.props = props;
		this.console = console;
	}

}
