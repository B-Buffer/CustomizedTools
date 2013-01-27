package com.customized.tools.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import com.customized.tools.po.Analyser;
import com.customized.tools.po.ClassSearcher;
import com.customized.tools.po.CustomizedToolsContext;
import com.customized.tools.po.DBTester;
import com.customized.tools.po.JMSTester;
import com.customized.tools.po.Monitor;
import com.customized.tools.po.Searcher;
import com.customized.tools.po.ToolsClassLoader;
import com.customized.tools.po.ToolsConfiguration;
import com.customized.tools.po.ToolsProfile;
import com.customized.tools.po.ToolsSubsystem;

/**
 * This class used to generate default toolsContent.xml configuration
 * @author kylin
 *
 */
public class ToolsContentGenerater {

	public static void main(String[] args) throws JAXBException, IOException {
		
		CustomizedToolsContext context = new CustomizedToolsContext();
		
		ToolsConfiguration configuration = new ToolsConfiguration();
		
		Analyser analyser = new Analyser();
		analyser.setPath("/home/kylin/work/test");
		configuration.setAnalyser(analyser);
		
		JMSTester jmsTester = new JMSTester();
		jmsTester.setFactoryJNDIName("ConnectionFactory");
		jmsTester.setFactoryClassName("org.jnp.interfaces.NamingContextFactory");
		jmsTester.setUrl("jnp://localhost:1099");
		jmsTester.setPkgs("org.jboss.naming:org.jnp.interfaces");
		jmsTester.setPrinciple("admin");
		jmsTester.setCredentials("admin");
		configuration.setJmsTester(jmsTester);
		
		DBTester dbTester = new DBTester();
		dbTester.setDriver("oracle.jdbc.OracleDriver");
		dbTester.setUrl("jdbc:oracle:thin:@//10.66.192.144:1521/JBOSS");
		dbTester.setUsername("GSSTEST");
		dbTester.setPassword("redhat");
		configuration.setDbTester(dbTester);
		
		ClassSearcher jarClassSearcher = new ClassSearcher();
		jarClassSearcher.setClassName("javax.net.ssl.KeyManager");
		jarClassSearcher.setFolderPath("/usr/java/jdk1.6.0_31");
		configuration.setJarClassSearcher(jarClassSearcher);
		
		Searcher fileSearcher = new Searcher();
		fileSearcher.setFileName("IIOPInternalInvoke");
		fileSearcher.setFolderPath("/home/kylin/work/test/build/demo");
		configuration.setFileSearcher(fileSearcher);
		
		Monitor fileChangeMonitor = new Monitor();
		fileChangeMonitor.setFolderPath("/home/kylin/work/tools/jboss-eap-6.0");
		fileChangeMonitor.setResultFile("eap6deployFromWebConsole.out");
		configuration.setFileChangeMonitor(fileChangeMonitor);
		
		context.setConfiguration(configuration);
		
		ToolsProfile profile = new ToolsProfile();
		List<ToolsSubsystem> list = loadToolsSubsystems();
		profile.setSubsystem(list);
		context.setProfile(profile);
		
		ToolsClassLoader classloader = new ToolsClassLoader();
		classloader.setPath("lib");
		classloader.setUrl("http://localhost:8080/lib");
		context.setClassloader(classloader);
		
	    JAXBUtil.getInstance().getMarshaller().marshal(context, new File("CustomizedToolsContext.xml"));
	    JAXBUtil.getInstance().getMarshaller().marshal(context, System.out);
	    
	}

	private static List<ToolsSubsystem> loadToolsSubsystems() throws IOException {
		
		List<ToolsSubsystem> list = new ArrayList<ToolsSubsystem> ();
		
		Map map = loadTools("tools.properties");
		
		for(Object obj : map.keySet()) {
			String name = (String) obj;
			String prompt = (String) map.get(name);

			ToolsSubsystem system = new ToolsSubsystem();
			system.setName(name);
			system.setPrompt(prompt);
			
			list.add(system);
		}
		
		return list;
	}
	
	private static Map loadTools(String conf) throws IOException {
		
		InputStream in = new FileInputStream(new File(conf));
		
		Properties prop = new Properties();
		
		prop.load(in);
		
		in.close();
		
		return prop;
	}
}
