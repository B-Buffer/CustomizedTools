package com.customized.tools.commands;

import java.io.IOException;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.commands.Validator.FileValidator;
import com.customized.tools.commands.Validator.InputNotNullValidator;
import com.customized.tools.gcviewer.GCViewerWrapper;
import com.customized.tools.model.GCViewerEntity;

@CommandDefinition(name="GCViewer", description = "JVM Garbage Collection Log Analyzer")
public class GCViewerWrapperCommand implements Command<CommandInvocation> {
	
	@Option(name = "filePath", 
			description = "GCViewer gc log file path", 
			defaultValue = {"gc.log"},
			validator = FileValidator.class)
	private String filePath;
	
	@Option(name = "resultFile", 
			description = "GCViewer result file name",
			defaultValue = {"export.csv"},
			validator = InputNotNullValidator.class)
	private String resultFile;
	
	GCViewerWrapper gc = new GCViewerWrapper(new InputConsole(), true);

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		
		GCViewerEntity entity = new GCViewerEntity();
		entity.setPath(filePath);
		entity.setName(resultFile);
		
		gc.setEntity(entity);
		
		gc.execute();
		
		return CommandResult.SUCCESS;
	}

}
