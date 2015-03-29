package com.customized.tools.cli.jboss.impl;

import java.io.IOException;

import org.jboss.as.cli.CommandLineException;
import org.jboss.as.controller.client.impl.AbstractModelControllerClient;
import org.jboss.as.protocol.mgmt.ManagementChannelAssociation;
import org.jboss.dmr.ModelNode;

import com.customized.tools.cli.jboss.CLIService;

public class CTSModelControllerClient extends AbstractModelControllerClient {
	
	private CLIService service;
	
	CTSModelControllerClient(String className) throws CommandLineException {
		try {
			service = (CLIService) Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new CommandLineException("Init CLI Service Server Impl failed", e);
		}
	}

	@Override
	public void close() throws IOException {
		service.close();
	}

	@Override
	protected ManagementChannelAssociation getChannelAssociation()throws IOException {
		return null;
	}

	@Override
	public ModelNode execute(ModelNode composite) throws IOException {
		return service.getComposites(composite);
	}

}
