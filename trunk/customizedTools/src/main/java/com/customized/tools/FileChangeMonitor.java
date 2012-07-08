package com.customized.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.customized.tools.filechangemonitor.FichangeMonitorException;
import com.customized.tools.filechangemonitor.FileChangeHandler;
import com.customized.tools.filechangemonitor.FileChangeListener;
import com.customized.tools.filechangemonitor.IFileChangeHandler;
import com.customized.tools.filechangemonitor.IFileChangeListener;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class FileChangeMonitor extends AbstractTools{
	
	private static final Logger logger = Logger.getLogger(FileChangeMonitor.class);
	
	private String persistFile;

	public String getPersistFile() {
		return persistFile;
	}

	public void setPersistFile(String persistFile) {
		this.persistFile = persistFile;
	}

	public FileChangeMonitor(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		final String monitorFolder = props.getProperty("monitor.folder", true);
		
		if( !new File(monitorFolder).exists()) {
			logger.error(new FichangeMonitorException(monitorFolder + "' does not exist"));
			console.prompt("  FileChangeMonitor exit, due to '" + monitorFolder + "' does not exist");
			Runtime.getRuntime().exit(0);
		}
		
		if(!new File(monitorFolder).isDirectory()) {
			logger.error(new FichangeMonitorException(monitorFolder + "' is not a directory"));
			console.prompt("  FileChangeMonitor exit, due to '" + monitorFolder + "' is not a directory");
			Runtime.getRuntime().exit(0);
		}
		
		setPersistFile(baseDir + "/" + props.getProperty("monitor.result.file", true));
		
		String prompt = "FileChangeMonitor Satrting, monitor on " + monitorFolder + ", Monitor result will persist to " + getPersistFile();
		
		logger.info(prompt);
		
		console.prompt(prompt);
		
		if(new File(getPersistFile()).exists()){
			new File(getPersistFile()).delete();
		}
		
		new File(getPersistFile()).createNewFile();
		
		final PrintWriter pw = new PrintWriter(new FileOutputStream(new File(getPersistFile())), true);
		
		new Thread(new Runnable(){

			public void run() {
				IFileChangeListener listener = new FileChangeListener(console, pw);
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
