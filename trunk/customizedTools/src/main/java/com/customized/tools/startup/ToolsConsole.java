package com.customized.tools.startup;

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

	private void validateFromConsole(Map<String, String> map) throws IOException {
		
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
	
	private void validate(List<ToolsConsoleEntity> list) throws IOException {
		
		Set<String> set = new HashSet<String>();
		String selected = null;
		StringBuffer sb = new StringBuffer();
		sb.append("Select: ");
		
		for(int i = 0 ; i < list.size() ; i ++) {
			ToolsConsoleEntity entity = list.get(i);
			sb.append(entity.getId() + ". " + entity.getMode() + " ");
			if(entity.isSelected()){
				selected = entity.getId() + "";
			}
			set.add(entity.getId() + "");
			prompt(entity.toString());
		}
		
		if(null != selected) {
			sb.append("[" + selected + "]");
		}
		
		UserInput input = new UserInput(set, sb.toString(), selected);
		input.validateMode();
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
		
		private String selected ;

		public UserInput(Set<String> set, String prompt, String selected ) {
			super();
			this.set = set;
			this.prompt = prompt;
			this.selected = selected;
		}
		
		public void validateMode() throws IOException {
			
			while(true) {
				String mode = getInput();
				
				String prompt = "You selected " + getMode(mode) + ", yes/no [yes]";
				
				prompt(prompt);
				
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String input = bufferRead.readLine();
				
				if(input.toLowerCase().equals("yes") || input.toLowerCase().equals("y") || input.equals("")) {
					setSelectedMode(mode);
					break;
				}
			}
			
		}
		
		private void setSelectedMode(String input) {
			
			for(int i = 0 ; i < list.size() ; i ++) {
				if((list.get(i).getId() + "").equals(input)){
					setMode(list.get(i).getMode());
					list.get(i).setSelected(true);
				}else {
					list.get(i).setSelected(false);
				}
			}
		}

		private String getMode(String input) {
			
			String mode = null;
			
			for(int i = 0 ; i < list.size() ; i ++) {
				if((list.get(i).getId() + "").equals(input)){
					mode = list.get(i).getMode();
				}
			}
			
			return mode;
		}

		private String getInput() throws IOException {
			
			String input = null;
			
			while(true) {
				
				prompt(prompt);
				
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				input = bufferRead.readLine();
			    
			    if(set.contains(input)) {
			    	break;
			    }
			    
			    if((null != selected) && input.equals("")){
			    	input = selected;
			    	break;
			    }
			}
			
			return input;
		}
	}
}
