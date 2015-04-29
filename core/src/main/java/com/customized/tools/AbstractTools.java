package com.customized.tools;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.Entity;

public abstract class AbstractTools implements ITool {
	
	protected InputConsole console;
	
	protected Entity entity;
	
	protected boolean isAesh = false;

	public AbstractTools(Entity entity, InputConsole console) {
		super();
		this.console = console;
		this.entity = entity;
	}
	
	public AbstractTools(Entity entity, InputConsole console, boolean isAesh) {
		super();
		this.console = console;
		this.entity = entity;
		this.isAesh = isAesh;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
