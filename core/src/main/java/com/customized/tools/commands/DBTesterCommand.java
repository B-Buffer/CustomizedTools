package com.customized.tools.commands;

import java.io.IOException;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.dbtester.DBConnectionTester;
import com.customized.tools.model.DBTester;

@CommandDefinition(name="dbTester", description = "Test Database Connection, Metadata")
public class DBTesterCommand implements Command<CommandInvocation> {
	
	
	@Option(name = "driver",
			description = "DB Driver Class",
			required = true,
			defaultValue = {"org.h2.Driver", "com.mysql.jdbc.Driver"})
	private String driverClass;
	
	@Option(name = "url",
			description = "DB Connection URL",
			required = true,
			defaultValue = {"jdbc:mysql://localhost:3306/test", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"})
	private String connURL;
	
	@Option(name = "user",
			description = "DB User",
			required = true,
			defaultValue = {"test_user", "sa"})
	private String user;
	
	@Option(name = "password",
			description = "DB Password",
			required = true,
			defaultValue = {"test_pass", "sa"})
	private String password;
	
	DBConnectionTester tester = new DBConnectionTester(null, new InputConsole());

	@Override
	public CommandResult execute(CommandInvocation commandInvocation)throws IOException, InterruptedException {
		
		DBTester entity = new DBTester();
		entity.setDriver(driverClass);
		entity.setUrl(connURL);
		entity.setUsername(user);
		entity.setPassword(password);
		
		tester.setEntity(entity);
		
		tester.execute();
		
		return CommandResult.SUCCESS;
	}

}
