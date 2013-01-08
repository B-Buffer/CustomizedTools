package com.customized.tools.filechangemonitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.customized.tools.common.console.InputConsole;
import com.customized.tools.common.po.Monitor;

public class FileChangeMonitor {
	
	private static final Logger logger = Logger.getLogger(FileChangeMonitor.class);
	
	private InputConsole console;
	
	private Monitor fileChangeMonitor;

	public String getPersistFile() {
		return fileChangeMonitor.getResultFile();
	}

	public FileChangeMonitor(Monitor fileChangeMonitor, InputConsole console) {
		
		this.fileChangeMonitor = fileChangeMonitor;
		this.console = console ;
	}

	public void execute() throws Throwable {
		
		final String monitorFolder = fileChangeMonitor.getFolderPath();
		
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
		
//		setPersistFile(fileChangeMonitor.getResultFile());
		
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
