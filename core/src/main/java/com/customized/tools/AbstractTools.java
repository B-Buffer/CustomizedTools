package com.customized.tools;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.Entity;

public abstract class AbstractTools implements ITool {
	
	protected InputConsole console;
	
	protected Entity entity;

	public AbstractTools(Entity entity, InputConsole console) {
		super();
		this.console = console;
		this.entity = entity;
	}

}
