package com.customized.tools.filechangemonitor;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileChangeEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private Date date = new Date();
	
	private String action = "";
	
	private File file = new File("");

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String toString() {
		
		return "[" + new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss").format(date) + "] - " + action + ": " + file.getAbsolutePath() ;
	}
}
