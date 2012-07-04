package com.kylin.tools.startup;

public class ToolsConsoleEntity implements Comparable<Object>{

	private int id;
	
	private String mode;
	
	private String prompt;
	
	private String cls;
	
	private boolean isSelected;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String toString() {
		return "  " + getId() + ". " + getMode() + " - " + getPrompt();
	}
	
	public static void main(String[] args) {
		
		ToolsConsoleEntity entity = new ToolsConsoleEntity();
		entity.setId(1);
		entity.setMode("jarClassSearcher");
		entity.setPrompt("select class from jar file");
		System.out.println(entity);
	}

	public int compareTo(Object obj) {

		ToolsConsoleEntity entity = (ToolsConsoleEntity) obj;
		
		return getId() - entity.getId();
	}
}
