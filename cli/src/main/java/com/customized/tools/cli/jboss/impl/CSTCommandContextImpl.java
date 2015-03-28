package com.customized.tools.cli.jboss.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jboss.aesh.console.settings.Settings;
import org.jboss.as.cli.CliConfig;
import org.jboss.as.cli.CliEventListener;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandCompleter;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandFormatException;
import org.jboss.as.cli.CommandHandler;
import org.jboss.as.cli.CommandHistory;
import org.jboss.as.cli.CommandLineCompleter;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.CommandRegistry;
import org.jboss.as.cli.batch.BatchManager;
import org.jboss.as.cli.batch.BatchedCommand;
import org.jboss.as.cli.handlers.HelpHandler;
import org.jboss.as.cli.handlers.HistoryHandler;
import org.jboss.as.cli.handlers.LsHandler;
import org.jboss.as.cli.impl.Console;
import org.jboss.as.cli.operation.CommandLineParser;
import org.jboss.as.cli.operation.NodePathFormatter;
import org.jboss.as.cli.operation.OperationCandidatesProvider;
import org.jboss.as.cli.operation.OperationRequestAddress;
import org.jboss.as.cli.operation.ParsedCommandLine;
import org.jboss.as.cli.operation.impl.DefaultCallbackHandler;
import org.jboss.as.cli.operation.impl.DefaultOperationCandidatesProvider;
import org.jboss.as.cli.operation.impl.DefaultOperationRequestAddress;
import org.jboss.as.cli.parsing.operation.OperationFormat;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

import com.customized.tools.cli.jboss.handlers.VersionHandler;
import com.customized.tools.model.version.Version;

public class CSTCommandContextImpl implements CommandContext {
	
	private static final Logger log = Logger.getLogger(CommandContext.class);

    /** the cli configuration */
    private final CliConfig config;

    private final CommandRegistry cmdRegistry = new CommandRegistry();
    
    private Console console;
    
    private String cmdLine;
    
    private DefaultCallbackHandler parsedCmd = new DefaultCallbackHandler(true);
    
    private Map<String, Object> map = new HashMap<String, Object>();
    
    private final OperationRequestAddress prefix = new DefaultOperationRequestAddress();
    
    private BufferedWriter outputTarget;
    
    private int exitCode;
    
    private final OperationCandidatesProvider operationCandidatesProvider;
    
    private final CommandCompleter cmdCompleter;
    
    private boolean terminate;
    
    private StringBuilder lineBuffer;
	
	CSTCommandContextImpl() throws CliInitializationException{
		this(false);
	}
	
	CSTCommandContextImpl(boolean initConsole) throws CliInitializationException{
		
		config = CliConfigImpl.load(this);
		
		initCommands();
		
		if (initConsole) {
			cmdCompleter = new CommandCompleter(cmdRegistry);
			initBasicConsole(null, null);
			operationCandidatesProvider = new DefaultOperationCandidatesProvider();
		} else {
			cmdCompleter = null;
			operationCandidatesProvider = null;
		}
		
	}

	private void initBasicConsole(InputStream consoleInput, OutputStream consoleOutput) throws CliInitializationException {
		copyConfigSettingsToConsole(consoleInput, consoleOutput);
		this.console = Console.Factory.getConsole(this);
	}

	private void copyConfigSettingsToConsole(InputStream consoleInput, OutputStream consoleOutput) {
		
		if(consoleInput != null){
			Settings.getInstance().setInputStream(consoleInput);
		}
		
        if(consoleOutput != null){
        	Settings.getInstance().setStdOut(consoleOutput);
        }
        
        Settings.getInstance().setHistoryDisabled(!config.isHistoryEnabled());
        Settings.getInstance().setHistoryFile(new File(config.getHistoryFileDir(), config.getHistoryFileName()));
        Settings.getInstance().setHistorySize(config.getHistoryMaxSize());
        Settings.getInstance().setEnablePipelineAndRedirectionParser(false);
	}

	private void initCommands() {
//		cmdRegistry.registerHandler(new PrefixHandler(), "cd", "cn");
//        cmdRegistry.registerHandler(new ClearScreenHandler(), "clear", "cls");
//        cmdRegistry.registerHandler(new CommandCommandHandler(cmdRegistry), "command");
        cmdRegistry.registerHandler(new HelpHandler(cmdRegistry), "help", "h");
        cmdRegistry.registerHandler(new HistoryHandler(), "history");
        cmdRegistry.registerHandler(new LsHandler(), "ls");
//        cmdRegistry.registerHandler(new PrintWorkingNodeHandler(), "pwd", "pwn");
//        cmdRegistry.registerHandler(new QuitHandler(), "quit", "q", "exit");
//        cmdRegistry.registerHandler(new ReadAttributeHandler(this), "read-attribute");
//        cmdRegistry.registerHandler(new ReadOperationHandler(this), "read-operation");
        cmdRegistry.registerHandler(new VersionHandler(), "version");
	}

	@Override
	public CliConfig getConfig() {
		return config;
	}

	@Override
	public String getArgumentsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParsedCommandLine getParsedCommandLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printLine(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printColumns(Collection<String> col) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminateSession() {
		terminate = true;
	}

	@Override
	public boolean isTerminated() {
		return terminate;
	}

	@Override
	public void set(String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelControllerClient getModelControllerClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectController(String host, int port)
			throws CommandLineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindClient(ModelControllerClient newClient) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectController() throws CommandLineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnectController() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDefaultControllerHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDefaultControllerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getControllerHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getControllerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommandLineParser getCommandLineParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationRequestAddress getCurrentNodePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodePathFormatter getNodePathFormatter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationCandidatesProvider getOperationCandidatesProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandHistory getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBatchMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BatchManager getBatchManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BatchedCommand toBatchedCommand(String line)
			throws CommandFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelNode buildRequest(String line) throws CommandFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandLineCompleter getDefaultCommandCompleter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDomainMode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addEventListener(CliEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getExitCode() {
		return exitCode;
	}

	@Override
	public void handle(String line) throws CommandLineException {
		
		if (line.isEmpty() || line.charAt(0) == '#') {
            return; // ignore comments
        }
		
		int i = line.length() - 1;
        while(i > 0 && line.charAt(i) <= ' ') {
            if(line.charAt(--i) == '\\') {
                break;
            }
        }
        if(line.charAt(i) == '\\') {
            if(lineBuffer == null) {
                lineBuffer = new StringBuilder();
            }
            lineBuffer.append(line, 0, i);
            lineBuffer.append(' ');
            return;
        } else if(lineBuffer != null) {
            lineBuffer.append(line);
            line = lineBuffer.toString();
            lineBuffer = null;
        }
        
        resetArgs(line);
        
        try {
			if (parsedCmd.getFormat() == OperationFormat.INSTANCE) {
				//TODO--
			} else {
				final String cmdName = parsedCmd.getOperationName();
			    CommandHandler handler = cmdRegistry.getCommandHandler(cmdName.toLowerCase());
			    if (handler != null) {
			    	handler.handle(this);
			    } else {
			        throw new CommandLineException("Unexpected command '" + line + "'. Type 'help --commands' for the list of supported commands.");
			    }
			}
		} catch(CommandLineException e) {
            throw e;
        } catch(Throwable t) {
            throw new CommandLineException("Failed to handle '" + line + "'", t);
        } finally {
            // so that getArgumentsString() doesn't return this line
            // during the tab-completion of the next command
            cmdLine = null;
        }
	}
	
	private void resetArgs(String cmdLine) throws CommandFormatException {
        if (cmdLine != null) {
            parsedCmd.parse(prefix, cmdLine);
            setOutputTarget(parsedCmd.getOutputTarget());
        }
        this.cmdLine = cmdLine;
    }

	private void setOutputTarget(String filePath) {
		if (filePath == null) {
            this.outputTarget = null;
            return;
        }
        FileWriter writer;
        try {
            writer = new FileWriter(filePath, false);
        } catch (IOException e) {
            error(e.getLocalizedMessage());
            return;
        }
        this.outputTarget = new BufferedWriter(writer);
	}

	@Override
	public void handleSafe(String line) {
		exitCode = 0;
        try {
            handle(line);
        } catch(Throwable t) {
            final StringBuilder buf = new StringBuilder();
            buf.append(t.getLocalizedMessage());
            Throwable t1 = t.getCause();
            while (t1 != null) {
                if (t1.getLocalizedMessage() != null) {
                    buf.append(": ").append(t1.getLocalizedMessage());
                } else {
                    t1.printStackTrace();
                }
                t1 = t1.getCause();
            }
            error(buf.toString());
        }
	}

	private void error(String message) {
		this.exitCode = 1 ;
		printLine(message);
	}

	@Override
	public void interact() {

		if(cmdCompleter == null) {
            throw new IllegalStateException("The console hasn't been initialized at construction time.");
        }
		
		printLine("Type 'help' for the list of supported commands.");
		
		try {
			while (!isTerminated()) {
				final String line = console.readLine(getPrompt());
                if (line == null) {
                    terminateSession();
                } else {
                    handleSafe(line.trim());
                }
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private String getPrompt() {
		
		if(lineBuffer != null) {
            return "> ";
        }
		
		StringBuilder buffer = new StringBuilder();
		
		buffer.append('[');
		buffer.append(Version.name() + " ");
		
		if (prefix.isEmpty()) {
            buffer.append('/');
        } else {
            buffer.append(prefix.getNodeType());
            final String nodeName = prefix.getNodeName();
            if (nodeName != null) {
                buffer.append('=').append(nodeName);
            }
        }
		
		buffer.append("] ");
        return buffer.toString();
	}

	@Override
	public File getCurrentDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentDir(File dir) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isResolveParameterValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setResolveParameterValues(boolean resolve) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSilent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSilent(boolean silent) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTerminalWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTerminalHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
