package com.kylin.tools.startup;

import java.util.Map;

public class ToolsConsole {

	private String mode;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public ToolsConsole(String mode) {
		super();
		this.mode = mode;
	}

	public String getToolClass(ToolsProperties props, Map<String, String> map) {
		
		validateFromConsole(map);
		
		props.setProperty("mode", mode);
		
		return map.get(mode);
	}

	private void validateFromConsole(Map<String, String> map) {
		// TODO Auto-generated method stub
		
	}
	
	public void prompt(String prompt){
		System.out.println(prompt);
	}
}
