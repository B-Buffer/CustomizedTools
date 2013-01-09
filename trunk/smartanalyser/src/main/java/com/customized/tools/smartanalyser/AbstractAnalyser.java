package com.customized.tools.smartanalyser;

import java.util.Set;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Analyser;

public abstract class AbstractAnalyser implements IAnalyser {

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	protected Set<String> imgSet;
	
	protected Analyser analyser;
	
	protected InputConsole console;

	public AbstractAnalyser(int status, Analyser analyser, InputConsole console, Set<String> imgSet) {
		super();
		this.status = status;
		this.analyser = analyser;
		this.console = console;
		this.imgSet = imgSet;
	}
}
