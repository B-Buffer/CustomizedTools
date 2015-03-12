package com.customized.tools.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.customized.tools.model.ClassSearcher;
import com.customized.tools.model.CustomizedToolsContext;
import com.customized.tools.model.DBTester;
import com.customized.tools.model.GCViewerEntity;
import com.customized.tools.model.Monitor;
import com.customized.tools.model.Searcher;
import com.customized.tools.model.TDAEntity;
import com.customized.tools.model.ToolsConfiguration;
import com.customized.tools.model.ToolsProfile;
import com.customized.tools.model.ToolsSubsystem;

/**
 * This class used to generate default CustomizedToolsContext.xml configuration
 * 
 * @author kylin
 *
 */
public class ToolsContentGenerater {
	
	public static void main(String[] args) throws JAXBException, IOException {
		String parentPath = "target";
		if(args.length == 1) {
			parentPath = args[0];
		}
		generate(parentPath);
	}

	
	public static void generate(String path) throws JAXBException, IOException{

		CustomizedToolsContext context = new CustomizedToolsContext();
		
		ToolsConfiguration configuration = new ToolsConfiguration();
		
		GCViewerEntity gcViewer = new GCViewerEntity();
		gcViewer.setPath("/home/kylin/tmp/gc.log");
		gcViewer.setName("export.csv");
		gcViewer.setId("GCViewer");
		configuration.setGcViewer(gcViewer);
		
		TDAEntity tda = new TDAEntity();
		tda.setPath("/home/kylin/tmp/tdump.out");
		tda.setId("TDA");
		configuration.setTda(tda);
		
		DBTester dbTester = new DBTester();
		dbTester.setDriver("com.mysql.jdbc.Driver");
		dbTester.setUrl("jdbc:mysql://localhost:3306/test");
		dbTester.setUsername("test_user");
		dbTester.setPassword("test_pass");
		dbTester.setId("dbConnectionTester");
		configuration.setDbTester(dbTester);
		
		ClassSearcher jarClassSearcher = new ClassSearcher();
		jarClassSearcher.setClassName("org.jboss.modules.Main");
		jarClassSearcher.setFolderPath("/home/kylin/server/jboss-eap-6.3");
		jarClassSearcher.setId("jarClassSearcher");
		configuration.setJarClassSearcher(jarClassSearcher);
		
		Searcher fileSearcher = new Searcher();
		fileSearcher.setFileName("Main");
		fileSearcher.setFolderPath("/home/kylin/server/jboss-eap-6.3");
		fileSearcher.setId("fileSearcher");
		configuration.setFileSearcher(fileSearcher);
		
		Monitor fileChangeMonitor = new Monitor();
		fileChangeMonitor.setFolderPath("/home/kylin/server/jboss-eap-6.3");
		fileChangeMonitor.setResultFile("TeiidInstall.out");
		fileChangeMonitor.setId("fileChangeMonitor");
		configuration.setFileChangeMonitor(fileChangeMonitor);
		
		context.setConfiguration(configuration);
		
		ToolsProfile profile = new ToolsProfile();
		List<ToolsSubsystem> list = loadToolsSubsystems();
		profile.setSubsystem(list);
		context.setProfile(profile);
		
	    JAXBUtil.getInstance().getMarshaller().marshal(context, new File(new File(path), "CustomizedToolsContext.xml"));
	    JAXBUtil.getInstance().getMarshaller().marshal(context, System.out);
	}

	private static List<ToolsSubsystem> loadToolsSubsystems() throws IOException {
		
		List<ToolsSubsystem> list = new ArrayList<ToolsSubsystem> ();
		
		Map<?,?> map = IOUtil.loadClassPathProps(ToolsContentGenerater.class.getClassLoader(), "tools.properties");
		
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
	
}
