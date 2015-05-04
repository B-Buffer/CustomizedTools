package com.customized.tools;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jboss.aesh.console.AeshConsole;
import org.jboss.aesh.console.AeshConsoleBuilder;
import org.jboss.aesh.console.Prompt;
import org.jboss.aesh.console.command.registry.AeshCommandRegistryBuilder;
import org.jboss.aesh.console.command.registry.CommandRegistry;
import org.jboss.aesh.console.helper.ManProvider;
import org.jboss.aesh.console.settings.Settings;
import org.jboss.aesh.console.settings.SettingsBuilder;
import org.jboss.aesh.terminal.Color;
import org.jboss.aesh.terminal.TerminalColor;
import org.jboss.aesh.terminal.TerminalString;

import com.customized.tools.commands.DBTesterCommand;
import com.customized.tools.commands.ExitCommand;
import com.customized.tools.commands.FileChangeMonitorCommand;
import com.customized.tools.commands.FileSearcherCommand;
import com.customized.tools.commands.GCViewerWrapperCommand;
import com.customized.tools.commands.JVMConfigCommand;
import com.customized.tools.commands.JarClassSearcherCommand;
import com.customized.tools.commands.TDAWrapperCommand;

public class AeshContainer implements LifeCycle {
	
	private final static Logger logger = Logger.getLogger(AeshContainer.class);
	
	private String rootNode = File.separator;
	
	private Status status = Status.STOP ;

	@Override
	public void doInit() {
		
		
		
		setStatus(Status.INIT);
	}

	@Override
	public void doStart() {
		
		if(status == Status.DESTORY || status == Status.STOP) {
			doInit();
		}
		
		logger.info("Container start up");
		
		AeshConsole console = buildAeshConsole();
		
//		console.getShell().println(Version.VERSION_STRING + " Started");
		
		setStatus(Status.START);
		
		console.start();
		
	}

	private AeshConsole buildAeshConsole() {
		
		Settings settings = new SettingsBuilder().logging(true)
				.enableMan(true)
				.readInputrc(false)
				.create();
		
		CommandRegistry registry = new AeshCommandRegistryBuilder()
				.command(ExitCommand.class)
				.command(JarClassSearcherCommand.class)
				.command(FileSearcherCommand.class)
				.command(FileChangeMonitorCommand.class)
				.command(GCViewerWrapperCommand.class)
				.command(TDAWrapperCommand.class)
				.command(DBTesterCommand.class)
				.command(JVMConfigCommand.class)
				.create();
		
		AeshConsole aeshConsole = new AeshConsoleBuilder()
				.commandRegistry(registry)
				.manProvider(new ManProviderTools())
				.settings(settings)
				.prompt(buildRootPrompt(rootNode))
                .create();

		return aeshConsole;
	}
	
	public String getRootNode() {
		return rootNode;
	}

	public void setRootNode(String rootNode) {
		this.rootNode = rootNode;
	}

	protected Prompt buildRootPrompt(String name) {
		String head = "[CustomizedTools ";
		String tail = "]";
		String chars = head + name + tail;
		TerminalColor color = new TerminalColor(Color.GREEN, Color.DEFAULT, Color.Intensity.BRIGHT);
		TerminalString terminal = new TerminalString(chars, color);
		return new Prompt(terminal);
	}

	@Override
	public void doStop() {
		setStatus(Status.STOP);
	}

	@Override
	public void status() {
		
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public void destory() {
		

		if(status == Status.START || status == Status.INIT) {
			doStop();
		}
		
	}
	
	private class ManProviderTools implements ManProvider {

		@Override
		public InputStream getManualDocument(String commandName) {
			return null;
		}
		
	}

}
