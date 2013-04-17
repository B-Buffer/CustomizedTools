package com.customized.tools.persist.test;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class TestBase {

	static {
		ConsoleAppender console = new ConsoleAppender();
		String PATTERN = "%d %-5p [%c] (%t) %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
	}
}
