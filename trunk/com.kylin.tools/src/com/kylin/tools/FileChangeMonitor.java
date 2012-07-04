package com.kylin.tools;

import java.io.File;

import org.apache.log4j.Logger;

import com.kylin.tools.filechangemonitor.FileChangeHandler;
import com.kylin.tools.filechangemonitor.FileChangeListener;
import com.kylin.tools.filechangemonitor.IFileChangeHandler;
import com.kylin.tools.filechangemonitor.IFileChangeListener;
import com.kylin.tools.startup.ToolsConsole;
import com.kylin.tools.startup.ToolsProperties;

public class FileChangeMonitor extends AbstractTools{
	
	private static final Logger logger = Logger.getLogger(FileChangeMonitor.class);

	public FileChangeMonitor(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		final String monitorFolder = props.getProperty("monitorFolder", true);
		
		String prompt = "FileChangeMonitor Satrting, monitor on " + monitorFolder ;
		
		logger.info(prompt);
		
		console.prompt(prompt);
		
		new Thread(new Runnable(){

			public void run() {
				IFileChangeListener listener = new FileChangeListener(console);
				IFileChangeHandler handler = new FileChangeHandler();
				while(true){
					listener.addListener(handler, new File(monitorFolder));
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						logger.error(e);
					}
				}
			}
		}).start();
	}

}
