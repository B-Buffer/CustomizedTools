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
	
	public void testPrintChar() {
		
		String prefix_1 = "├──" ;
		String prefix_2 = "│";
		String prefix_3 = "└──" ;
		
		String blank_1 = "   ", blank_2 = " " ;	
		
		
		System.out.println(prefix_1.charAt(0) + " " + prefix_1.charAt(1) + " " + prefix_1.charAt(2));
		System.out.println();
		System.out.println(prefix_3.charAt(0) + " " + prefix_3.charAt(1) + " " + prefix_3.charAt(2));
	
		int a = prefix_1.charAt(0);
		int b = prefix_1.charAt(1);
		int c = prefix_1.charAt(2);
		
		int d = prefix_3.charAt(0);
		int e = prefix_3.charAt(1);
		int f = prefix_3.charAt(2);
		int g = prefix_2.charAt(0);
		
		System.out.println(a + " " + b + " " + c);
		System.out.println(d + " " + e + " " + f);
		System.out.println(g);
		
		char c1 = '|';
		char c2 = '-';
		int i1 = c1 ;
		int i2 = c2 ;
		System.out.println(i1 + " " + i2);
		
		System.out.println();
		
		System.out.println("/");
		System.out.println(prefix_1 + blank_2 + "a");
		System.out.println(prefix_2 + blank_1 + prefix_1 + blank_2 + "b");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_1 + blank_2 + "d");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_1 + blank_2 + "e");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_3 + blank_2 + "f");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_1 + blank_2 + "i");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 +  prefix_3 + blank_2 + "j");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + blank_2 + blank_1 + prefix_3 + blank_2 + "k");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + blank_2 + blank_1 + blank_2 + blank_1 + prefix_3 + blank_2 + "l");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_2 + blank_1 + blank_2 + blank_1 + blank_2 + blank_1 + blank_2 + blank_1 + prefix_3 + blank_2 + "m");
		System.out.println(prefix_2 + blank_1 + prefix_2 + blank_1 + prefix_3 + blank_2 + "h");
		System.out.println(prefix_2 + blank_1 + prefix_3 + blank_2 + "g");
		System.out.println(prefix_3 + blank_2 + "c");
	}

	private void testPlaceHolder() {

		String l3holder_1 = "├──", l3holder_2 = "└──", l3holder_3 = "   " ;
		String l1holder_1 = "│", l1holder_2 = " " ;
		
		System.out.println("/");
		System.out.println(l3holder_1 + l1holder_2 + "a");
		System.out.println(l1holder_1 + l3holder_3 + l3holder_1 + l1holder_2 + "b");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l3holder_1 + l1holder_2 + "d");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l3holder_1 + l1holder_2 + "e");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l3holder_2 + l1holder_2 + "f");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l3holder_1 + l1holder_2 + "i");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l3holder_2 + l1holder_2 + "j");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_2 + l3holder_3 + l3holder_2 + l1holder_2 + "k");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_2 + l3holder_3 + l1holder_2 + l3holder_3 + l3holder_2 + l1holder_2 + "l");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l1holder_2 + l3holder_3 + l1holder_2 + l3holder_3 + l1holder_2 + l3holder_3 + l3holder_2 + l1holder_2 + "m");
		System.out.println(l1holder_1 + l3holder_3 + l1holder_1 + l3holder_3 + l3holder_2 + l1holder_2 + "h");
		System.out.println(l1holder_1 + l3holder_3 + l3holder_2 + l1holder_2 + "g");
		System.out.println(l3holder_2 + l1holder_2 + "c");
	}
	
	public static void main(String[] args) {
		
		new Test().testPlaceHolder();
	}

	

}
