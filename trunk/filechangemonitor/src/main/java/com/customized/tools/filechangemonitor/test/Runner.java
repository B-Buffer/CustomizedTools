package com.customized.tools.filechangemonitor.test;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.filechangemonitor.FileChangeMonitor;
import com.customized.tools.po.Monitor;

public class Runner {

	public static void main(String[] args) {
		FileChangeMonitor searcher = new FileChangeMonitor(new Monitor(), new InputConsole());
		searcher.execute();
	}

}
