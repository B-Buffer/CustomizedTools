package com.customized.tools.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandOperation;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;
import org.jboss.aesh.terminal.Key;

import com.customized.tools.commands.Validator.DirectoryValidator;
import com.customized.tools.commands.Validator.InputNotNullValidator;
import com.customized.tools.filechangemonitor.FileChangeHandler;
import com.customized.tools.filechangemonitor.FileChangeListener;
import com.customized.tools.filechangemonitor.IFileChangeHandler;
import com.customized.tools.filechangemonitor.IFileChangeListener;
import com.customized.tools.model.Monitor;

@CommandDefinition(name="monitor", description = "Monitor a file system")
public class FileChangeMonitorCommand implements Command<CommandInvocation> {
	
	@Option(shortName = 'H', name = "help", hasValue = false,
            description = "display this help and exit")
    private boolean help;
	
	@Option(shortName = 'p',
			name = "folderPath", 
			description = "The FileChangeMonitor file system path", 
			defaultValue = {"/home/kylin/server/jboss-eap-6.3"},
			validator = DirectoryValidator.class)
	private String folderPath;
	
	@Option(shortName = 'n',
			name = "resultFile", 
			description = "The FileChangeMonitor result file name",
			defaultValue = {"monitor.out"},
			validator = InputNotNullValidator.class)
	private String resultFile;
	
//	FileChangeMonitor searcher = new FileChangeMonitor( true);

	@Override
	public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
		
		if(help) {
            commandInvocation.getShell().out().println(commandInvocation.getHelpInfo("find"));
            return CommandResult.SUCCESS;
        }
		
		Monitor entity = new Monitor();
		entity.setFolderPath(folderPath);
		entity.setResultFile(resultFile);
		
		commandInvocation.getShell().out().println("FileChangeMonitor will monitor on " + folderPath + ", Monitor result will persist to " + getPersistFile(resultFile));
		
		commandInvocation.getShell().out().println("are you sure you want start? (y/n) ");
		CommandOperation operation = null;
        try {
        	operation = commandInvocation.getInput();
        }
        catch (InterruptedException e) {
            return CommandResult.FAILURE;
        }
        
        if(operation.getInputKey() == Key.y){
        	reset();
    		final PrintWriter pw = new PrintWriter(new FileOutputStream(new File(getPersistFile(resultFile))), true);
    		IFileChangeListener listener = new FileChangeListener(commandInvocation.getShell().out(), pw);
    		IFileChangeHandler handler = new FileChangeHandler();
    		while(true){
    			listener.addListener(handler, new File(folderPath));
    			try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
    		}
        } 
        return CommandResult.SUCCESS;
	}
	
	private void reset() throws IOException {

		if(new File(getPersistFile(resultFile)).exists()){
			new File(getPersistFile(resultFile)).delete();
		}
		
		new File(getPersistFile(resultFile)).createNewFile();
		
	}

	private String getPersistFile(String resultFile ) {
		if(System.getProperty("cst.out.dir") == null) {
			return resultFile;
		}else {
			return System.getProperty("cst.out.dir") + File.separator + resultFile;
		}
	}

}
