package com.customized.tools.cli.test;

public class Test {

	public static void main(String[] args) {
		
		String str = " rm saa ada Xcd adad ~~ ad 1223 !@# ";
		for(String s : str.split(" ")) {
			System.out.println(s);
		}
	}

}
