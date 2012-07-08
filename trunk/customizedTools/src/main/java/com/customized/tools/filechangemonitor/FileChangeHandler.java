package com.customized.tools.filechangemonitor;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.customized.tools.startup.ToolsConsole;

public class FileChangeHandler implements IFileChangeHandler {
	
	private static final Logger logger = Logger.getLogger(FileChangeHandler.class);

	public void hander(List<FileChangeEntity> changeList, ToolsConsole console, PrintWriter pw) {
		
		for(FileChangeEntity entity : changeList) {
			console.prompt("  " + entity);
			
			if(pw != null) {
				pw.println(entity + "\n");
			}
			
			if(logger.isDebugEnabled()){
				logger.debug(entity);
			}
		}
	}

}
