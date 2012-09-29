package com.customized.tools;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class HelloWorldTool extends AbstractTools {

	public HelloWorldTool(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		console.prompt("Hello Wrold Tool Test");
	}

}
