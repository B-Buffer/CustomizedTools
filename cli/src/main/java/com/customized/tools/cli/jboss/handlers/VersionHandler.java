package com.customized.tools.cli.jboss.handlers;

import java.util.Collections;
import java.util.List;

import org.jboss.as.cli.CommandArgument;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandHandler;
import org.jboss.as.cli.CommandLineException;

import com.customized.tools.model.version.Version;

public class VersionHandler implements CommandHandler {
	
	public static final VersionHandler INSTANCE = new VersionHandler();

	@Override
	public boolean isAvailable(CommandContext ctx) {
		return true;
	}

	@Override
	public boolean isBatchMode(CommandContext ctx) {
		return false;
	}

	@Override
	public void handle(CommandContext ctx) throws CommandLineException {
		ctx.printLine(Version.versionString());
	}

	@Override
	public CommandArgument getArgument(CommandContext ctx, String name) {
		return null;
	}

	@Override
	public boolean hasArgument(CommandContext ctx, String name) {
		return false;
	}

	@Override
	public boolean hasArgument(CommandContext ctx, int index) {
		return false;
	}

	@Override
	public List<CommandArgument> getArguments(CommandContext ctx) {
		return Collections.emptyList();
	}

}
