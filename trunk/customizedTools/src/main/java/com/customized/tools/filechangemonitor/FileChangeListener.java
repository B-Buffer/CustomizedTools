package com.customized.tools.filechangemonitor;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.customized.tools.startup.ToolsConsole;

public class FileChangeListener implements IFileChangeListener {
	
	private ToolsConsole console;
	
	private PrintWriter pw;
	
	public FileChangeListener(ToolsConsole console, PrintWriter pw) {
		super();
		this.console = console;
		this.pw = pw;
	}

	private Set<File> contentSet = null;

	public void addListener(IFileChangeHandler handler, File file) {
		if(null == contentSet) {
			contentSet = new HashSet<File>();
			initializeContentSet(contentSet, file);
		}
		
		List<FileChangeEntity> changeList = new ArrayList<FileChangeEntity>();
		
		Set<File> newSet = new HashSet<File>();
		initializeContentSet(newSet, file);
		
		for(File tmp : newSet) {
			if(!contentSet.contains(tmp)) {
				FileChangeEntity entity = new FileChangeEntity();
				entity.setAction("Add");
				entity.setFile(tmp);
				changeList.add(entity);
			}
		}
		
		for(File tmp : contentSet) {
			if(!newSet.contains(tmp)) {
				FileChangeEntity entity = new FileChangeEntity();
				entity.setAction("Delete");
				entity.setFile(tmp);
				changeList.add(entity);
			}
		}
		
		for(FileChangeEntity entity: changeList) {
			if(entity.getAction().equals("Add")) {
				contentSet.add(entity.getFile());
			} else if(entity.getAction().equals("Delete")) {
				contentSet.remove(entity.getFile());
			}
		}
		
		handler.hander(changeList, console, pw);
	}
	
	private void initializeContentSet(Set<File> contentSet, File file) {
		
		if(file.isDirectory()) {
			for(File tmp : file.listFiles()) {
				initializeContentSet(contentSet, tmp);
			}
		}
		
		contentSet.add(file);
	}

}
