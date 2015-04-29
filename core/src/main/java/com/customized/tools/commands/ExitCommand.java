package com.customized.tools.commands;

import java.io.IOException;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

@CommandDefinition(name="exit", description = "Exit CustomizedTools")
public class ExitCommand implements Command<CommandInvocation>{

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		commandInvocation.stop();
        return CommandResult.SUCCESS;
	}

}
