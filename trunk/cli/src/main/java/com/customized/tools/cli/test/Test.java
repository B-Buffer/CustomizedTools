package com.customized.tools.cli.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.cli.TreeInputConsoleException;
import com.customized.tools.cli.TreeNode;

public class Test extends InputConsole{
	
	public void testRecursiveOrder() {
		
		TreeNode a = new TreeNode("a", "[key=value]", null, null);
		TreeNode b = new TreeNode("b", "[key=value]", a, null);
		TreeNode c = new TreeNode("c", "[key=value]", a, null);
		a.addSon(b).addSon(c);
		TreeNode d = new TreeNode("d", "[key=value]", b, null);
		TreeNode e = new TreeNode("e", "[key=value]", d, null);
		b.addSon(d);
		d.addSon(e);
		TreeNode rootNode = new TreeNode("/", "", null, a);
		
		StringBuffer sb = new StringBuffer();
		sb.append(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss SSS]").format(new Date()) + " TreeNode Content Stack:");
		sb.append("\n");
		recursiveOrder(rootNode, sb, 0);
		
		test();
		
		System.out.println(sb.toString());
//		out.print(sb.toString());
//		out.flush();
		
	}
	
	private void recursiveOrder(TreeNode node, StringBuffer sb, int index) {
		
		String preBlank = countBlank(index ++);
		sb.append(preBlank + node.getName() + " - [" + node.getContent() + "]" );
		sb.append("\n");
		
		for(TreeNode son : node.getSons()) {
			recursiveOrder(son, sb, index);
		}
		
	}

	private String countBlank(int size) {

		String tab = "    ";
		String sum = "";
		for(int i = 0 ; i < size ; i ++) {
			sum += tab ;
		}
		return sum;
	}

	private PrintWriter out = null ;
	
	public void test() {
		try {
			File file = new File(System.currentTimeMillis() + ".log");
			out = new PrintWriter(new FileWriter(file), false);
			prompt("TreeInputConsole TreeNode Debug information will output to " + file.getName());
		} catch (IOException e) {
			throw new TreeInputConsoleException("instantiate PrintWriter error", e);
		}
	}

	public static void main(String[] args) {
		
		new Test().testRecursiveOrder();
	}

}
