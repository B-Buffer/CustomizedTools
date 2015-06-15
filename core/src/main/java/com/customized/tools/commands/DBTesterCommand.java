package com.customized.tools.commands;

import java.io.IOException;
import java.sql.Connection;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;

import com.customized.tools.AeshContainer;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.commands.Validator.DBDriverValidator;
import com.customized.tools.commands.Validator.DBTesterOptionValidator;
import com.customized.tools.commands.Validator.DBURLValidator;
import com.customized.tools.dbtester.DBConnectionTester;
import com.customized.tools.dbtester.JDBCMetdataProcessor;
import com.customized.tools.dbtester.JDBCUtil;
import com.customized.tools.dbtester.metdata.Metadata;
import com.customized.tools.model.DBTester;

@CommandDefinition(name="dbTester", description = "Test Database Connection, Metadata")
public class DBTesterCommand implements Command<CommandInvocation> {
	
	
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
	private String option;
	
	DBConnectionTester tester = new DBConnectionTester(null, new InputConsole());

	@Override
	public CommandResult execute(CommandInvocation commandInvocation)throws IOException, InterruptedException {
		
		if(help) {
            commandInvocation.getShell().out().println(commandInvocation.getHelpInfo("find"));
            return CommandResult.SUCCESS;
        }
		
		DBTester entity = new DBTester();
		entity.setDriver(driverClass);
		entity.setUrl(connURL);
		entity.setUsername(user);
		entity.setPassword(password);
		
		switch(option){
		case "DBConnectionTest" :
			dbconnectionTest(entity);
			break;
		case "DBMetadataTest" :
			dbmetadataTest(commandInvocation, entity);
			break;
		case "SQLPlus" :
			sqlplus(commandInvocation, entity);
			break;
		}
		
		return CommandResult.SUCCESS;
	}

	private void sqlplus(CommandInvocation commandInvocation, DBTester entity) throws InterruptedException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(commandInvocation.getShell().in().getStdError()));
	
//		commandInvocation.print(commandInvocation.getPrompt().getPromptAsString());
//		StringBuffer sb = new StringBuffer();
//		while(true){
//			Key key = commandInvocation.getInput().getInputKey();
//			sb.append(key);
//			if(key != Key.ESC && key != Key.END && key != Key.HOME && key != Key.F && key != Key.UP && key != Key.DOWN){
//				commandInvocation.print(key.getAsChar() + "");
//			}
//			
//			if(key == Key.SEMI_COLON) {
//				commandInvocation.println("");
//				break;
//			}
//		}
//		commandInvocation.println(sb.toString());
//		commandInvocation.println(commandInvocation.getPrompt().getPromptAsString());
		
		commandInvocation.println("Start New console");
		
		new AeshContainer().doStart();
		commandInvocation.println("Exit New console");

	}

	private void dbmetadataTest(CommandInvocation commandInvocation, DBTester entity) {
		try {
			Connection conn = JDBCUtil.getConnection(entity.getDriver(), entity.getUrl(), entity.getUsername(), entity.getPassword());
			Metadata metdata = new Metadata();
			JDBCMetdataProcessor processor = new JDBCMetdataProcessor();
			processor.process(metdata, conn);
			commandInvocation.println(metdata.getTables().toString());
			JDBCUtil.close(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dbconnectionTest(DBTester entity) {
		tester.setEntity(entity);
		tester.execute();
	}

}
