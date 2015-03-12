package com.customized.tools.filechangemonitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.customized.tools.ITool;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.Monitor;

public class FileChangeMonitor implements ITool {
	
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

	public void execute() {
		
		logger.info("FileChangeMonitor Start");
		
		try {
			if(console.readFromCli(fileChangeMonitor.getId())) {
				String folder = console.readFolderPath("Input FileChangeMonitor folder path", fileChangeMonitor.getFolderPath(), true);
				fileChangeMonitor.setFolderPath(folder);
				String file = console.readString("Input FileChangeMonitor result file name", fileChangeMonitor.getResultFile(), false);
				fileChangeMonitor.setResultFile(file);
			}
			
			final String monitorFolder = fileChangeMonitor.getFolderPath();
			
			if( !new File(monitorFolder).exists()) {
				console.prompt(new FichangeMonitorException("'" + monitorFolder + "' does not exist").getMessage());
				return;
			}
			
			if(!new File(monitorFolder).isDirectory()) {
				console.prompt(new FichangeMonitorException("'" + monitorFolder + "' is not a directory").getMessage());
				return;
			}
						
			String prompt = "FileChangeMonitor monitor on " + monitorFolder + ", Monitor result will persist to " + getPersistFile();
						
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
							logger.error("", e);
						}
					}
				}
			}).start();
		} catch (Exception e) {
			FichangeMonitorException ex = new FichangeMonitorException("FichangeMonitor Error", e);
			console.prompt(ex.getMessage());
			logger.error("", ex);
			throw ex;
		}
	}

}
