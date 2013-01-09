package com.customized.tools.filechangemonitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Monitor;

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

	public void execute() {
		
		logger.info("FileChangeMonitor Start");
		
		try {
			if(console.readFromCli("FileChangeMonitor")) {
				String folder = console.readFolderPath("Input FileChangeMonitor folder path [</home/kylin/work/eap/jboss-eap-6.0>]", true);
				fileChangeMonitor.setFolderPath(folder);
				String file = console.readFilePath("Input FileChangeMonitor result file name [<result.log>]", false);
				fileChangeMonitor.setResultFile(file);
			}
			
			final String monitorFolder = fileChangeMonitor.getFolderPath();
			
			if( !new File(monitorFolder).exists()) {
				throw new Exception("'" + monitorFolder + "' does not exist");
			}
			
			if(!new File(monitorFolder).isDirectory()) {
				throw new Exception("'" + monitorFolder + "' is not a directory");
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
