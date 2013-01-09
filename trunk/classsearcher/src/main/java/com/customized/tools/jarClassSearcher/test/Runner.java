package com.customized.tools.jarClassSearcher.test;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.jarClassSearcher.JarClassSearcher;
import com.customized.tools.po.ClassSearcher;

public class Runner {

	public static void main(String[] args) {
		JarClassSearcher searcher = new JarClassSearcher(new ClassSearcher(), new InputConsole());
		searcher.execute();
	}

}
