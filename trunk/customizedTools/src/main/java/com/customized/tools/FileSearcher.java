package com.customized.tools;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.customized.tools.fileSearcher.FileSearcherException;
import com.customized.tools.fileSearcher.Searcher;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class FileSearcher extends AbstractTools {
	
	private final static Logger logger = Logger.getLogger(FileSearcher.class);

	public FileSearcher(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		try {
			String searchName = props.getProperty("searcher.fileName", true);
			String searchFolder = props.getProperty("searcher.folder", true);
			
			File parentfile = new File(searchFolder);
			
			if(! parentfile.exists() || !parentfile.isDirectory() || searchName.length() == 0) {
				throw new FileSearcherException("Search folder not exist.");
			}
			
			console.prompt("FileSearcher start, searching file '" + searchName + "' under " + searchFolder);
			
			List<String> result = new Searcher(searchName, searchFolder, console).search();
			
			console.prompt("\n" + searchName + " be found " + result.size() + " times:");
			
			for(int i = 0 ; i < result.size() ; i ++) {
				console.prompt("   " + result.get(i));
			}
		} catch (Exception e) {

			console.prompt(errNote(e));
			
			logger.error(errProp, e);
		}
	}

}
