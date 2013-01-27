package com.customized.tools.common;

import java.io.File;

import javax.xml.bind.Unmarshaller;

import com.customized.tools.po.Analyser;
import com.customized.tools.po.ClassSearcher;
import com.customized.tools.po.DBTester;
import com.customized.tools.po.JMSTester;
import com.customized.tools.po.Monitor;
import com.customized.tools.po.Searcher;
import com.customized.tools.po.ToolsSubsystem;

/**
 * Supply public method for other component, If component need underlying configuration
 * 
 * @author kylin
 *
 */
public class Configuration {
	
	private ToolsSubsystem toolsContent;
	
	public Configuration (String path) {
		
		try {
			Unmarshaller unmarshaller = JAXBUtil.getInstance().getUnmarshaller();
			toolsContent = (ToolsSubsystem) unmarshaller.unmarshal(new File(path));
		} catch (Exception e) {
			throw new ConfigurationLoaderException("load configuration file throw exception", e);
		}
	}
	
//	public Analyser getAnalyser() {
//		return toolsContent.getAnalyser();
//	}
//	
//	public JMSTester getJmsTester() {
//		return toolsContent.getJmsTester();
//	}
//	
//	public DBTester getDbTester() {
//		return toolsContent.getDbTester();
//	}
//	
//	public ClassSearcher getJarClassSearcher() {
//		return toolsContent.getJarClassSearcher();
//	}
//	
//	public Searcher getFileSearcher() {
//		return toolsContent.getFileSearcher();
//	}
//	
//	public Monitor getFileChangeMonitor() {
//		return toolsContent.getFileChangeMonitor();
//	}

}
