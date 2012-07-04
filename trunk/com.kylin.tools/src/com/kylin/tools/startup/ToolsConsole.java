package com.kylin.tools.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ToolsConsole {

	private String mode;
	
	List<ToolsConsoleEntity> list = new ArrayList<ToolsConsoleEntity>();

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

	public String getToolClass(ToolsProperties props, String conf) throws IOException {
		
		Map<String, String> map = loadTools(conf);
		
		validateFromConsole(map);
		
		props.setProperty("mode", mode);
		
		String cls = null;
		for(int i = 0 ; i < list.size() ; i ++) {
			if(list.get(i).isSelected()) {
				cls = list.get(i).getCls();
			}
		}
		
		if(cls != null) {
			return cls;
		}
		
		throw new RuntimeException("can not find the tool class");
	}

	private void validateFromConsole(Map<String, String> map) {
		
		String ending = ".prompt";
		
		int id = 1;
		
		String cls = map.get(mode);
		if(cls != null) {
			ToolsConsoleEntity entity = new ToolsConsoleEntity();
			entity.setId(id++);
			entity.setMode(mode);
			entity.setCls(map.get(mode));
			entity.setPrompt(map.get(mode + ending));
			entity.setSelected(true);
			list.add(entity);
			map.remove(mode);
			map.remove(mode + ending); 
		}
		
		for(String str : map.keySet()){
			if(!str.endsWith(ending)) {
				ToolsConsoleEntity entity = new ToolsConsoleEntity();
				entity.setId(id++);
				entity.setMode(str);
				entity.setCls(map.get(str));
				entity.setPrompt(map.get(str + ending));
				entity.setSelected(false);
				list.add(entity);
			}
		}
		
		prompt("Select a tool:");
		
		validate(list);
		
	}
	
	private void validate(List<ToolsConsoleEntity> list) {
		
		Set<String> set = new HashSet<String>();
		
		for(int i = 0 ; i < list.size() ; i ++) {
			ToolsConsoleEntity entity = list.get(i);
			prompt();
		}
	}

	private Map loadTools(String conf) throws IOException {
		
		InputStream in = new FileInputStream(new File(conf));
		
		Properties prop = new Properties();
		
		prop.load(in);
		
		in.close();
		
		return prop;
	}
	
	public void prompt(String prompt){
		System.out.println(prompt);
	}
	
	private class UserInput {
		
		private Set<String> set ;
		
		private String prompt;

		public UserInput(Set<String> set, String prompt) {
			super();
			this.set = set;
			this.prompt = prompt;
		}
		
		public String getInput() throws IOException {
			
			String input = null;
			
			while(true) {
				
				prompt(prompt);
				
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				input = bufferRead.readLine();
			    
			    if(set.contains(input)) {
			    	break;
			    }
			}
			
			return input;
		}
	}
}
