package com.customized.tools.searcher;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.customized.tools.common.console.InputConsole;
import com.customized.tools.common.po.Searcher;

public class FileSearcher {
	
	private final static Logger logger = Logger.getLogger(FileSearcher.class);

	private InputConsole console;
	
	private Searcher fileSearcher ;
	
	public FileSearcher(Searcher fileSearcher, InputConsole console) {
		
		this.fileSearcher = fileSearcher ;
		this.console = console ;
	}

	public void execute() {
		
		logger.info("FileSearcher Start");
		
		String msg = "Run FileSearcher From Command Line\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No";
		int a = '1';
		int b = '2';
		int res = console.read(msg, a, b);
		
		try {
			if(res == a) {
				String folder = console.readFolderPath("Input FileSearcher folder path [</home/kylin/work/eap/jboss-eap-6.0>]", true);
				fileSearcher.setFolderPath(folder);
				String fileName = console.readFilePath("Input FileSearcher file name [<Main.class>]", false);
				fileSearcher.setFileName(fileName);
			}
			
			String searchName = fileSearcher.getFileName();
			String searchFolder = fileSearcher.getFolderPath();
			
			File parentfile = new File(searchFolder);
			
			if(! parentfile.exists() || !parentfile.isDirectory() || searchName.length() == 0) {
				throw new FileSearcherException("Search folder not exist.");
			}
			
			console.prompt("FileSearcher start, searching file '" + searchName + "' under " + searchFolder);
			
			List<String> result = new SearcherImpl(searchName, searchFolder, console).search();
			
			console.prompt(searchName + " be found " + result.size() + " times:");
			
			for(int i = 0 ; i < result.size() ; i ++) {
				console.println("    " + result.get(i));
			}
		} catch (Exception e) {
			FileSearcherException ex = new FileSearcherException("File Searcher return a Exception" ,e);
			console.prompt(ex.getMessage());
			logger.error("", ex);
			throw ex ;
		}
	}

}
