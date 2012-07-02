package com.kylin.tools;

import com.kylin.tools.startup.Tool;
import com.kylin.tools.startup.ToolProperties;

public abstract class AbstractTools extends Tool {
	
	private ToolProperties props;
	
	public AbstractTools(ToolProperties props) {
		super();
		this.props = props;
	}

}
