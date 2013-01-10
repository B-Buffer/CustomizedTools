package com.customized.tools.jmstester;

import java.util.List;

import com.customized.tools.cli.wizard.Wizard;
import com.customized.tools.po.JMSTester;

public class JMSTesterWizard extends Wizard {
	
	private JMSTester jmsTester ;

	public JMSTesterWizard(JMSTester jmsTester) {
		super("JMSConnectionTester");
		this.jmsTester = jmsTester;
	}
	
	String factoryJNDIName =  "FactoryJNDIName";
	String factoryClassName = "FactoryClassName";
	String url = "Url";
	String pkgs = "Pkgs";
	String principle = "Principle" ;
	String credentials = "Credentials";
	
	public void doInit() {
		
		List<String> list = getOrderList();
		String key = factoryJNDIName ;
		list.add(key);
		update(key, jmsTester.getFactoryJNDIName());
		
		key = factoryClassName;
		list.add(key);
		update(key, jmsTester.getFactoryClassName());
		
		key = url ;
		list.add(key);
		update(key, jmsTester.getUrl());
		
		key = pkgs ;
		list.add(key);
		update(key, jmsTester.getPkgs());
		
		key = principle ;
		list.add(key);
		update(key, jmsTester.getPrinciple());
		
		key = credentials;
		list.add(key);
		update(key, jmsTester.getCredentials());
		
	}
	
	public JMSTester getJMSTester() {
		JMSTester tester = new JMSTester();
		tester.setFactoryClassName(getContent().get(factoryClassName));
		tester.setFactoryJNDIName(getContent().get(factoryJNDIName));
		tester.setUrl(getContent().get(url));
		tester.setPkgs(getContent().get(pkgs));
		tester.setPrinciple(getContent().get(principle));
		tester.setCredentials(getContent().get(credentials));
		return tester;   
	}

}
