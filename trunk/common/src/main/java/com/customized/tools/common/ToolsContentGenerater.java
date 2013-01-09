package com.customized.tools.common;

import java.io.File;

import javax.xml.bind.JAXBException;

import com.customized.tools.po.Analyser;
import com.customized.tools.po.ClassSearcher;
import com.customized.tools.po.DBTester;
import com.customized.tools.po.JMSTester;
import com.customized.tools.po.Monitor;
import com.customized.tools.po.Searcher;
import com.customized.tools.po.ToolsContent;

/**
 * This class used to generate default toolsContent.xml configuration
 * @author kylin
 *
 */
public class ToolsContentGenerater {

	public static void main(String[] args) throws JAXBException {
		
		ToolsContent content = new ToolsContent();
		
		Analyser analyser = new Analyser();
		analyser.setPath("/home/kylin/work/test");
		content.setAnalyser(analyser);
		
		JMSTester jmsTester = new JMSTester();
		jmsTester.setFactoryJNDIName("ConnectionFactory");
		jmsTester.setFactoryClassName("org.jnp.interfaces.NamingContextFactory");
		jmsTester.setUrl("jnp://localhost:1099");
		jmsTester.setPkgs("org.jboss.naming:org.jnp.interfaces");
		jmsTester.setPrinciple("admin");
		jmsTester.setCredentials("admin");
		content.setJmsTester(jmsTester);
		
		DBTester dbTester = new DBTester();
		dbTester.setDriver("oracle.jdbc.OracleDriver");
		dbTester.setUrl("jdbc:oracle:thin:@//10.66.192.144:1521/JBOSS");
		dbTester.setUsername("GSSTEST");
		dbTester.setPassword("redhat");
		content.setDbTester(dbTester);
		
		ClassSearcher jarClassSearcher = new ClassSearcher();
		jarClassSearcher.setClassName("javax.net.ssl.KeyManager");
		jarClassSearcher.setFolderPath("/usr/java/jdk1.6.0_31");
		content.setJarClassSearcher(jarClassSearcher);
		
		Searcher fileSearcher = new Searcher();
		fileSearcher.setFileName("IIOPInternalInvoke");
		fileSearcher.setFolderPath("/home/kylin/work/test/build/demo");
		content.setFileSearcher(fileSearcher);
		
		Monitor fileChangeMonitor = new Monitor();
		fileChangeMonitor.setFolderPath("/home/kylin/work/tools/jboss-eap-6.0");
		fileChangeMonitor.setResultFile("eap6deployFromWebConsole.out");
		content.setFileChangeMonitor(fileChangeMonitor);
	    
	    JAXBUtil.getInstance().getMarshaller().marshal(content, new File("toolsContent.xml"));
	    JAXBUtil.getInstance().getMarshaller().marshal(content, System.out);
	    
	}
}
