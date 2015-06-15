package com.customized.tools.filechangemonitor;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FileChangeListener implements IFileChangeListener {
	
	private PrintStream out;
	
	private PrintWriter pw;
	
	public FileChangeListener(PrintStream out, PrintWriter pw) {
		super();
		this.out = out;
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
		
		handler.hander(changeList, out, pw);
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
