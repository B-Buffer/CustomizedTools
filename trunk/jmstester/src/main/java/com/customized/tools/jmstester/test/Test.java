package com.customized.tools.jmstester.test;

import com.customized.tools.cli.WizardConsole;
import com.customized.tools.jmstester.JMSConnectionTester;
import com.customized.tools.po.JMSTester;

public class Test {

	public static void main(String[] args) {
		
		JMSTester jmsTester = new JMSTester();
		jmsTester.setFactoryJNDIName("ConnectionFactory");
		jmsTester.setFactoryClassName("org.jnp.interfaces.NamingContextFactory");
		jmsTester.setUrl("jnp://localhost:1099");
		jmsTester.setPkgs("org.jboss.naming:org.jnp.interfaces");
		jmsTester.setPrinciple("admin");
		jmsTester.setCredentials("admin");
		jmsTester.setDeplibraries("lib");
		
		new JMSConnectionTester(jmsTester, new WizardConsole()).execute();
	}

}
