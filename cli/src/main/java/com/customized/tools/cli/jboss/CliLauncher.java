package com.customized.tools.cli.jboss;

import java.util.Collections;
import java.util.List;

import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;

import com.customized.tools.cli.jboss.handlers.VersionHandler;
import com.customized.tools.cli.jboss.impl.CSTCommandContextFactory;

public class CliLauncher {

	public static void main(String[] args) {

		int exitCode = 0;
        CommandContext cmdCtx = null;
        
//        args = new String[]{"--version"};
        
        try {
			List<String> commands = null;
			boolean version = false;
			
			for(String arg : args) {
				if("--version".equals(arg)) {
			        version = true;
			    } else if (arg.equals("--help") || arg.equals("-h")) {
			        commands = Collections.singletonList("help");
			    }
			}
			
			if(version) {
				cmdCtx = initCommandContext(false);
				VersionHandler.INSTANCE.handle(cmdCtx);
				return;
			}
			
			if(commands != null) {
				cmdCtx = initCommandContext(false);
				processCommands(commands, cmdCtx);
                return;
			}
			
			cmdCtx = initCommandContext(true);
			cmdCtx.interact();
			
		} catch (Throwable t) {
			t.printStackTrace();
            exitCode = 1;
		} finally {
			if(cmdCtx != null && cmdCtx.getExitCode() != 0) {
	        	exitCode = cmdCtx.getExitCode();
	        }
		}
        System.exit(exitCode);
	}
	
	private static CommandContext initCommandContext(boolean initConsole) throws CliInitializationException {
		final CommandContext cmdCtx = CSTCommandContextFactory.getInstance().newCommandContext(initConsole);
		return cmdCtx;
	}

	private static void processCommands(List<String> commands, CommandContext cmdCtx) {
		int i = 0;
        try {
            while (cmdCtx.getExitCode() == 0 && i < commands.size() && !cmdCtx.isTerminated()) {
                cmdCtx.handleSafe(commands.get(i));
                ++i;
            }
        } finally {
            cmdCtx.terminateSession();
        }
	}

}
