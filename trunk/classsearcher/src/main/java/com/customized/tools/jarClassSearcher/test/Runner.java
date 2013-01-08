package com.customized.tools.jarClassSearcher.test;

import com.customized.tools.common.console.InputConsole;
import com.customized.tools.common.po.ClassSearcher;
import com.customized.tools.jarClassSearcher.JarClassSearcher;

public class Runner {

	public static void main(String[] args) {
		JarClassSearcher searcher = new JarClassSearcher(new ClassSearcher(), new InputConsole());
		searcher.execute();
	}

}
