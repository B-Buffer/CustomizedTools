package com.kylin.tools.filechangemonitor;

import java.util.List;

import org.apache.log4j.Logger;

import com.kylin.tools.startup.ToolConsole;

public class FileChangeHandler implements IFileChangeHandler {
	
	private static final Logger logger = Logger.getLogger(FileChangeHandler.class);

	public void hander(List<FileChangeEntity> changeList, ToolConsole console) {
		
		for(FileChangeEntity entity : changeList) {
			console.prompt("  " + entity);
			
			if(logger.isDebugEnabled()){
				logger.debug(entity);
			}
		}
	}

}
