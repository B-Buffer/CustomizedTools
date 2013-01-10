package com.customized.tools.cli.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Wizard  {
	
	private String name;
	
	public Wizard(String name) {
		this.name = name ;
	}

	private static final long serialVersionUID = -4826125378899032182L;

	List<String> orderList = new ArrayList<String>();
	
	Map<String, String> preContent = new HashMap<String, String>();
	
	Map<String, String> content = new HashMap<String, String>();
	
	public String getKey(int id) {
		return orderList.get(id);
	}
	
	public void update(int id, String value) {
		content.put(orderList.get(id), value);
	}
	
	public void update(String key, String value) {
		content.put(key, value);
		preContent.put(key, value);
	}
	
	public List<String> getOrderList() {
		return orderList;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public abstract void doInit();
	
	public void doNext() {
		preContent.clear();
		preContent.putAll(content);
	}
	
	public void doPre() {
		content.clear();
		content.putAll(preContent);
	}
	
	public String getPrompt() {
		
		String start = "---------------------- " + name + " Wizard ----------------------------";
		String end = "";
		for(int i = 0 ; i < start.length() ; i ++) {
			end += "-";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(start);
		for(int i = 0 ; i < orderList.size() ; i ++) {
			sb.append("  [" + (i + 1) + "]. " + orderList.get(i) + ": " + content.get(orderList.get(i)));
		}
		sb.append("\n                [p]. Previous  [n]. Next  [o]. Ok");
		sb.append(end);
		
		return sb.toString();
	}
	
}
