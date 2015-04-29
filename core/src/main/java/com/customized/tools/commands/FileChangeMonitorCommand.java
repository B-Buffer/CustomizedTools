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
import com.customized.tools.filechangemonitor.FileChangeMonitor;
import com.customized.tools.model.Monitor;

@CommandDefinition(name="fileChangeMonitor", description = "Monitor a file system")
public class FileChangeMonitorCommand implements Command<CommandInvocation> {
	
	@Option(name = "folderPath", 
			description = "FileChangeMonitor folder path", 
			defaultValue = {"/home/kylin/server/jboss-eap-6.3"},
			validator = DirectoryValidator.class)
	private String folderPath;
	
	@Option(name = "resultFile", 
			description = "FileChangeMonitor result file name",
			defaultValue = {"monitor.out"},
			validator = InputNotNullValidator.class)
	private String resultFile;
	
	FileChangeMonitor searcher = new FileChangeMonitor(new InputConsole(), true);

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		
		Monitor entity = new Monitor();
		entity.setFolderPath(folderPath);
		entity.setResultFile(resultFile);
		
		searcher.setEntity(entity);
		
		searcher.execute();
		
		return CommandResult.SUCCESS;
	}

}
