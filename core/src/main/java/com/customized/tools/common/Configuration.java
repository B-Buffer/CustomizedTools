package com.customized.tools.common;

import java.io.File;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import com.customized.tools.model.ClassSearcher;
import com.customized.tools.model.CustomizedToolsContext;
import com.customized.tools.model.DBTester;
import com.customized.tools.model.GCViewerEntity;
import com.customized.tools.model.Monitor;
import com.customized.tools.model.Searcher;
import com.customized.tools.model.TDAEntity;
import com.customized.tools.model.ToolsProfile;
import com.customized.tools.model.ToolsSubsystem;

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
			throw new ToolsCommonException("load configuration file throw exception", e);
		}
	}
	
	public CustomizedToolsContext getContext() {
		return context ;
	}
	
	public GCViewerEntity getGcViewer() {
		return context.getConfiguration().getGcViewer();
	}
	
	public TDAEntity getTda() {
		return context.getConfiguration().getTda();
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
