package com.customized.tools.searcher;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.customized.tools.ITool;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.Searcher;

public class FileSearcher implements ITool {
	
	private final static Logger logger = Logger.getLogger(FileSearcher.class);

	private InputConsole console;
	
	private Searcher fileSearcher ;
	
	public FileSearcher(Searcher fileSearcher, InputConsole console) {
		
		this.fileSearcher = fileSearcher ;
		this.console = console ;
	}

	public void execute() {
		
		logger.info("FileSearcher Start");
		
		try {
			if(console.readFromCli(fileSearcher.getId())) {
				String folder = console.readFolderPath("Input FileSearcher folder path", fileSearcher.getFolderPath(), true);
				fileSearcher.setFolderPath(folder);
				String fileName = console.readString("Input FileSearcher file name", fileSearcher.getFileName(), false);
				fileSearcher.setFileName(fileName);
			}
			
			String searchName = fileSearcher.getFileName();
			String searchFolder = fileSearcher.getFolderPath();
			
			File parentfile = new File(searchFolder);
			
			if(!parentfile.exists() || !parentfile.isDirectory() || searchName.length() == 0) {
				console.prompt(new FileSearcherException("Search folder not exist.").getMessage());
				return;
			}
			
			if( searchName.length() == 0 || searchName.trim().length() == 0) {
				console.prompt(new FileSearcherException("Error search file name format").getMessage());
				return;
			}
			
			console.prompt("FileSearcher start, searching file '" + searchName + "' under " + searchFolder);
			
			List<String> result = new SearcherImpl(searchName, searchFolder, console).search();
			
			String propmtStr = "File '" + searchName + "' be found " + result.size() + " times:";
			console.prompt(propmtStr);
			
			for(int i = 0 ; i < result.size() ; i ++) {
				console.println(console.twoTab() + result.get(i));
			}
			
			//TODO-- add dump propmtStr, result to file
		} catch (Exception e) {
			FileSearcherException ex = new FileSearcherException("File Searcher return a Exception" ,e);
			console.prompt(ex.getMessage());
			logger.error("", ex);
			throw ex ;
		}
	}

}
