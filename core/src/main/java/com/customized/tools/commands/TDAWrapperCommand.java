package com.customized.tools.commands;

import java.io.IOException;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.commands.Validator.FileValidator;
import com.customized.tools.model.TDAEntity;
import com.customized.tools.tda.TDAWrapper;

@CommandDefinition(name="TDA", description = "Java TDA Analyzer")
public class TDAWrapperCommand implements Command<CommandInvocation> {
	
	@Option(name = "path", 
			description = "TDA file path", 
			defaultValue = {"tdump.out"},
			validator = FileValidator.class)
	private String path;
	
	TDAWrapper tda = new TDAWrapper(new InputConsole(), true);

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		
		TDAEntity entity = new TDAEntity();
		entity.setPath(path);
		
		tda.setEntity(entity);
		
		tda.execute();
		
		return CommandResult.SUCCESS;
	}

}
