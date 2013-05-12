package com.customized.tools.dbtester;

import java.util.List;

import com.customized.tools.cli.wizard.Wizard;
import com.customized.tools.po.DBTester;

public class DBTesterWizard extends Wizard {
	
	private DBTester dbTester ;

	public DBTesterWizard(DBTester dbTester) {
		super("DBConnectionTester");
		this.dbTester = dbTester;
		doInit();
	}
	
	String driver = "Driver";
	String url = "Url";
	String username = "Username";
	String password = "Password";
	String driverlib = "Driverlib";

	public void doInit() {

		List<String> list = getOrderList();
		String key = driver ;
		list.add(key);
		update(key, dbTester.getDriver());
		
		key = url ;
		list.add(key);
		update(key, dbTester.getUrl());
		
		key = username ;
		list.add(key);
		update(key, dbTester.getUsername());
		
		key = password ;
		list.add(key);
		update(key, dbTester.getPassword());
		
		key = driverlib ;
		list.add(key);
		update(key, dbTester.getDriverlib());
		
		updateKeyLength();
	}
	
	public DBTester getDBTester() {
		DBTester tester = new DBTester();
		tester.setDriver(getContent().get(driver));
		tester.setUrl(getContent().get(url));
		tester.setUsername(getContent().get(username));
		tester.setPassword(getContent().get(password));
		tester.setDriverlib(getContent().get(driverlib));
		return tester ;
	}

}
