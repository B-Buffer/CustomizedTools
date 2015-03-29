package com.customized.tools.cli.jboss.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandFormatException;
import org.jboss.as.cli.Util;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;
import org.jboss.as.cli.impl.ArgumentWithValue;
import org.jboss.as.cli.impl.ArgumentWithoutValue;
import org.jboss.as.cli.operation.OperationRequestAddress;
import org.jboss.as.cli.operation.OperationRequestCompleter;
import org.jboss.as.cli.operation.ParsedCommandLine;
import org.jboss.as.cli.operation.impl.DefaultOperationRequestAddress;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;

public class LsHandler extends CommandHandlerWithHelp {
	
	private final ArgumentWithValue nodePath;
	private final ArgumentWithoutValue l;

	public LsHandler() {
		this("ls");
	}

	public LsHandler(String command) {
		super(command, false);
		l = new ArgumentWithoutValue(this, "-l");
		nodePath = new ArgumentWithValue(this, OperationRequestCompleter.ARG_VALUE_COMPLETER, 0, "--node-path");
	}

	@Override
	public boolean isAvailable(CommandContext ctx) {
		return true;
	}

	@Override
	protected void doHandle(CommandContext ctx) throws CommandFormatException {
		
		final ParsedCommandLine parsedCmd = ctx.getParsedCommandLine();
		String nodePath = this.nodePath.getValue(parsedCmd);
		
		final OperationRequestAddress address;
        if (nodePath != null){
        	address = null;
        	//TODO--
        } else {
        	address = new DefaultOperationRequestAddress(ctx.getCurrentNodePath());
        } 
        
        List<String> names = null;
        if(address.endsOnType()) {
            final String type = address.getNodeType();
            address.toParentNode();
            names = Util.getNodeNames(ctx.getModelControllerClient(), address, type);
            printList(ctx, names, l.isPresent(parsedCmd));
            return;
        }
        
        final ModelNode composite = new ModelNode();
        composite.get(Util.OPERATION).set(Util.COMPOSITE);
        composite.get(Util.ADDRESS).setEmptyList();
        
        ModelNode outcome;
        try {
            outcome = ctx.getModelControllerClient().execute(composite);
        } catch (IOException e) {
            throw new CommandFormatException("Failed to read resource: " + e.getLocalizedMessage(), e);
        }
        
        names = new ArrayList<>();
        for(ModelNode tool : outcome.get(Util.RESULT).asList()){
        	Property props = tool.asProperty();
			String key = props.getName();
			String value = tool.get(key).asString();
			if(l.isPresent(parsedCmd)) {
				names.add(key + " : " + value);
			} else {
				names.add(key);
			}
        }
        
        if(names != null) {
            printList(ctx, names, l.isPresent(parsedCmd));
        }
        
	}


}
