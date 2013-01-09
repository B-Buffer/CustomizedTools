package com.customized.tools.searcher.test;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Searcher;
import com.customized.tools.searcher.FileSearcher;

public class Runner {

	public static void main(String[] args) {
		FileSearcher searcher = new FileSearcher(new Searcher(), new InputConsole());
		searcher.execute();
	}

}
