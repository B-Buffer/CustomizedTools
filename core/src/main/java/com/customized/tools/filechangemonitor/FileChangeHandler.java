package com.customized.tools.filechangemonitor;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

public class FileChangeHandler implements IFileChangeHandler {

	public void hander(List<FileChangeEntity> changeList, PrintStream out, PrintWriter pw) {
		
		for(FileChangeEntity entity : changeList) {
			
			out.println("  " + entity);
			
			if(pw != null) {
				pw.println(entity + "\n");
			}
		}
	}

}
