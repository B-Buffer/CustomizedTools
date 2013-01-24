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
	
	/**
	 * The TreeInputConsole Test TreeNode architecture
	 *         a
	 *        / \
	 *       b   c
	 *      /
	 *     d
	 *    /
	 *   e
	 * a is root node, has 2 son nodes b and c ;  
	 * b has a son node d and father node is a ;
	 * c is leaf node, father node is a ;
	 * d has father node b and son node e ;
	 * e is leaf node and father node is d ;
	 * 
	 * @throws IOException
	 */
	protected void testTreeNode() throws IOException {
		
		TreeNode a = new TreeNode("a", "[key=value]", null, null);
		TreeNode b = new TreeNode("b", "[key=value]", a, null);
		TreeNode c = new TreeNode("c", "[key=value]", a, null);
		a.addSon(b).addSon(c);
		TreeNode d = new TreeNode("d", "[key=value]", b, null);
		TreeNode e = new TreeNode("e", "[key=value]", d, null);
		b.addSon(d);
		d.addSon(e);
		
		TreeInputConsole test = new TreeInputConsole("test", a, true);
//		test.start();
//		TreeNode node = test.getTreeNode("/asd");
//		System.out.println(node);
	}
	
	protected void testTreeNodeNULL() throws IOException {
		TreeInputConsole test = new TreeInputConsole("test", null);
//		test.start();
	}

	public static void main(String[] args) throws IOException {
		
		TreeInputConsoleTest test = new TreeInputConsoleTest();
		
		test.testTreeNode() ;
		
		test.testTreeNodeNULL() ;
	}
}
