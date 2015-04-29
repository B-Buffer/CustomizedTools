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
import com.customized.tools.jarClassSearcher.JarClassSearcher;
import com.customized.tools.model.ClassSearcher;

@CommandDefinition(name="jarClassSearcher", description = "Search class from jar file")
public class JarClassSearcherCommand implements Command<CommandInvocation>{
	
	@Option(name = "folderPath", 
			description = "jarClassSearcher folder path", 
			defaultValue = {"/home/kylin/server/jboss-eap-6.3"},
			validator = DirectoryValidator.class)
	private String folderPath;
	
	@Option(name = "className", 
			description = "jarClassSearcher class name",
			defaultValue = {"org.jboss.modules.Main"},
			validator = InputNotNullValidator.class)
	private String className;
	
	JarClassSearcher searcher = new JarClassSearcher(new InputConsole(), true);

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {

		
		ClassSearcher entity = new ClassSearcher();
		entity.setFolderPath(folderPath);
		entity.setClassName(className);
		
		searcher.setEntity(entity);
		
		searcher.execute();
		
		return CommandResult.SUCCESS;
	}

}
