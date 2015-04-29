package com.customized.tools.commands;

import java.io.IOException;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.commands.Validator.DirectoryValidator;
import com.customized.tools.commands.Validator.InputNotNullValidator;
import com.customized.tools.model.Searcher;
import com.customized.tools.searcher.FileSearcher;

@CommandDefinition(name="fileSearcher", description = "Search file from a folder, even this file exist in jar, zip, war, ear, esb")
public class FileSearcherCommand implements Command<CommandInvocation> {
	
	@Option(name = "folderPath", 
			description = "FileSearcher folder path", 
			defaultValue = {"/home/kylin/server/jboss-eap-6.3"},
			validator = DirectoryValidator.class)
	private String folderPath;
	
	@Option(name = "fileName", 
			description = "FileSearcher file name",
			defaultValue = {"Main", ".xml"},
			validator = InputNotNullValidator.class)
	private String fileName;
	
	FileSearcher searcher = new FileSearcher(new InputConsole(), true);

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		
		Searcher entity = new Searcher();
		entity.setFileName(fileName);
		entity.setFolderPath(folderPath);
		
		searcher.setEntity(entity);
		
		searcher.execute();
		
		return CommandResult.SUCCESS;
	}

}
