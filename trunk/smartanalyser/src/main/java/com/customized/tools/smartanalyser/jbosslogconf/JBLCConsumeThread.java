package com.customized.tools.smartanalyser.jbosslogconf;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;

public class JBLCConsumeThread extends JBLCThreadBase implements Runnable {
	
	private static final Logger logger = Logger.getLogger(JBLCConsumeThread.class);

	private InputConsole console;
	
	public JBLCConsumeThread(Threshold threshold, InputConsole console) {
		super(threshold);
		this.console = console;
	}

	public void run() {
		
		setActive(true);
		
		logThreadStatus(getName());
		
		while(isActive()) {
			
		}
	}

	public String getName() {
		return JBLCConsumeThread.class.getSimpleName();
	}

}
