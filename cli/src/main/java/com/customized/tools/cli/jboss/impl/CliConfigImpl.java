package com.customized.tools.cli.jboss.impl;

import org.jboss.as.cli.CliConfig;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.SSLConfig;

public class CliConfigImpl implements CliConfig {
	
	private boolean validateOperationRequests;
	
	private boolean resolveParameterValues;
	
	private boolean historyEnabled;
    private String historyFileName;
    private String historyFileDir;
    private int historyMaxSize;
    
    private boolean silent;
	
	static CliConfig load(final CommandContext ctx) throws CliInitializationException {
		//TODO-- add init from parse config file
		return new CliConfigImpl();
	}
	
	private CliConfigImpl() {
		
		validateOperationRequests = true;
		
		resolveParameterValues = false;
		
		historyEnabled = true;
        historyFileName = ".cst-cli-history";
        historyFileDir = System.getProperty("user.home");
        historyMaxSize = 500;
        
        silent = false;
	}

	@Override
	public String getDefaultControllerHost() {
		return null;
	}

	@Override
	public int getDefaultControllerPort() {
		return 0;
	}

	@Override
	public boolean isHistoryEnabled() {
		return historyEnabled;
	}

	@Override
	public String getHistoryFileName() {
		return historyFileName;
	}

	@Override
	public String getHistoryFileDir() {
		return historyFileDir;
	}

	@Override
	public int getHistoryMaxSize() {
		return historyMaxSize;
	}

	@Override
	public int getConnectionTimeout() {
		return 0;
	}

	@Override
	public SSLConfig getSslConfig() {
		return null;
	}

	@Override
	public boolean isValidateOperationRequests() {
		return validateOperationRequests;
	}

	@Override
	public boolean isResolveParameterValues() {
		return resolveParameterValues;
	}

	@Override
	public boolean isSilent() {
		return silent;
	}

}
