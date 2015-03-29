package com.customized.tools.cli.jboss;

import java.io.Closeable;

import org.jboss.dmr.ModelNode;

public interface CLIService  extends Closeable{

	public ModelNode getComposites(ModelNode composite);
}
