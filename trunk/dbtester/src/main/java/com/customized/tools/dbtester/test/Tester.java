package com.customized.tools.dbtester.test;

import com.customized.tools.cli.WizardConsole;
import com.customized.tools.dbtester.DBConnectionTester;
import com.customized.tools.po.DBTester;

public class Tester {

	public static void main(String[] args) {
		
		DBTester dbTester = new DBTester();
		dbTester.setDriver("oracle.jdbc.OracleDriver");
		dbTester.setUrl("jdbc:oracle:thin:@//10.66.192.144:1521/JBOSS");
		dbTester.setUsername("GSSTEST");
		dbTester.setPassword("redhat");

		DBConnectionTester tester = new DBConnectionTester(dbTester, new WizardConsole());
		tester.execute();
	}

}
