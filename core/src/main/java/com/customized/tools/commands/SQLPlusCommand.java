package com.customized.tools.commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

import com.customized.tools.commands.Validator.DBDriverValidator;
import com.customized.tools.commands.Validator.DBURLValidator;
import com.customized.tools.dbtester.JDBCUtil;

@CommandDefinition(name="sqlPlus", description = "A SQLPlus for ")
public class SQLPlusCommand implements Command<CommandInvocation> {
	
	@Option(shortName = 'H', name = "help", hasValue = false,
            description = "display this help and exit")
    private boolean help;
	
	@Option(shortName = 'd',
			name = "driver",
			description = "DB Driver Class",
			required = true,
			defaultValue = {"org.h2.Driver", "com.mysql.jdbc.Driver", "org.postgresql.Driver"},
			validator = DBDriverValidator.class)
	private String driverClass;
	
	@Option(shortName = 'c',
			name = "url",
			description = "DB Connection URL",
			required = true,
			defaultValue = {"jdbc:mysql://localhost:3306/test", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"},
			validator = DBURLValidator.class)
	private String connURL;
	
	@Option(shortName = 'u',
			name = "user",
			description = "DB User",
			required = true,
			defaultValue = {"test_user", "sa"})
	private String user;
	
	@Option(shortName = 'p',
			name = "password",
			description = "DB Password",
			required = true,
			defaultValue = {"test_pass", "sa"})
	private String password;

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		
		if(help) {
            commandInvocation.getShell().out().println(commandInvocation.getHelpInfo("find"));
            return CommandResult.SUCCESS;
        }
		
		commandInvocation.getShell().out().print("SQLPlus>");
		commandInvocation.getShell().out().println(commandInvocation.getInputLine());
		
		try {
			Connection conn = JDBCUtil.getConnection(driverClass, connURL, user, password);
			while(true){
				commandInvocation.getShell().out().print("SQLPlus>");
				String input = commandInvocation.getInputLine();
				if(input.equals("exit")){
					break;
				}
				commandInvocation.getShell().out().println(input);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			commandInvocation.getShell().out().println(e);
		}
		return CommandResult.SUCCESS;
	}

}
