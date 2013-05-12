package com.customized.tools.common;

import java.io.File;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import com.customized.tools.po.Analyser;
import com.customized.tools.po.ClassSearcher;
import com.customized.tools.po.CustomizedToolsContext;
import com.customized.tools.po.DBTester;
import com.customized.tools.po.JMSTester;
import com.customized.tools.po.Monitor;
import com.customized.tools.po.Searcher;
import com.customized.tools.po.ToolsClassLoader;
import com.customized.tools.po.ToolsProfile;
import com.customized.tools.po.ToolsSubsystem;

/**
 * Supply public method for other component, If component need underlying configuration
 * 
 * @author kylin
 *
 */
public class Configuration {
	
	private CustomizedToolsContext context;
	
	public Configuration (String path) {
		
		try {
			Unmarshaller unmarshaller = JAXBUtil.getInstance().getUnmarshaller();
			context = (CustomizedToolsContext) unmarshaller.unmarshal(new File(path));
		} catch (Exception e) {
			throw new ConfigurationLoaderException("load configuration file throw exception", e);
		}
	}
	
	public CustomizedToolsContext getContext() {
		return context ;
	}
	
	public Analyser getAnalyser() {
		return context.getConfiguration().getAnalyser();
	}
	
	public JMSTester getJmsTester() {
		return context.getConfiguration().getJmsTester();
	}
	
	public DBTester getDbTester() {
		return context.getConfiguration().getDbTester();
	}
	
	public ClassSearcher getJarClassSearcher() {
		return context.getConfiguration().getJarClassSearcher();
	}
	
	public Searcher getFileSearcher() {
		return context.getConfiguration().getFileSearcher();
	}
	
	public Monitor getFileChangeMonitor() {
		return context.getConfiguration().getFileChangeMonitor();
	}
	
	public List<ToolsSubsystem> getSubsystem() {
		return context.getProfile().getSubsystem();
	}
	
	public ToolsProfile getProfile() {
		return context.getProfile();
	}

}
