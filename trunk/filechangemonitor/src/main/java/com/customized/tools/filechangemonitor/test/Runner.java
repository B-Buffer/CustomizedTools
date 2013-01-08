package com.customized.tools.filechangemonitor.test;

import com.customized.tools.common.console.InputConsole;
import com.customized.tools.common.po.Searcher;
import com.customized.tools.filechangemonitor.FileSearcher;

public class Runner {

	public static void main(String[] args) {
		FileSearcher searcher = new FileSearcher(new Searcher(), new InputConsole());
		searcher.execute();
	}

}
