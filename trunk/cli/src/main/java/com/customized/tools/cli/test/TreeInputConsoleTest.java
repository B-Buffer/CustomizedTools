package com.customized.tools.cli.test;

import java.io.IOException;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;

/**
 * Demo for use TreeInputConsole
 * 
 * @author kylin
 *
 */
public class TreeInputConsoleTest {

	public static void main(String[] args) throws IOException {
		
		TreeNode a = new TreeNode("a", "[key=value]", null, null);
		TreeNode b = new TreeNode("b", "[key=value]", a, null);
		TreeNode c = new TreeNode("c", "[key=value]", a, null);
		a.addSon(b).addSon(c);
		TreeNode d = new TreeNode("d", "[key=value]", b, null);
		TreeNode e = new TreeNode("e", "[key=value]", d, null);
		b.addSon(d);
		d.addSon(e);
		
		TreeInputConsole test = new TreeInputConsole("test", e);
		test.execute();
	}
}
