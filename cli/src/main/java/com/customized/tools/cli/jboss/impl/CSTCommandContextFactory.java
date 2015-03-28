package com.customized.tools.cli.jboss.impl;

import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.impl.CommandContextFactoryImpl;

public class CSTCommandContextFactory extends CommandContextFactoryImpl {

	public static CSTCommandContextFactory getInstance() {
		return new CSTCommandContextFactory();
	}
	
	protected CSTCommandContextFactory() {}

	@Override
	public CommandContext newCommandContext() throws CliInitializationException {
		return new CSTCommandContextImpl();
	}
	
	public CommandContext newCommandContext(boolean initConsole) throws CliInitializationException {
		return new CSTCommandContextImpl(initConsole);
	}
	
	

}
