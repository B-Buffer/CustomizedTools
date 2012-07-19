package com.customized.tools.smartanalyser.jbosslogconf;

import java.util.ArrayList;
import java.util.List;

public class FileEntity {

	private int id;
	
	private String path;
	
	private List<JBossLogConfEntity> entity = new ArrayList<JBossLogConfEntity>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<JBossLogConfEntity> getEntity() {
		return entity;
	}
	
	public void add(JBossLogConfEntity context) {
		entity.add(context);
	}
}
