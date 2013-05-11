package com.customized.tools.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import samurai.wrapper.SamuraiWrapper;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;
import com.customized.tools.cli.WizardConsole;
import com.customized.tools.common.Configuration;
import com.customized.tools.dbtester.DBConnectionTester;
import com.customized.tools.filechangemonitor.FileChangeMonitor;
import com.customized.tools.gcviewer.GCViewerWrapper;
import com.customized.tools.jarClassSearcher.JarClassSearcher;
import com.customized.tools.jmstester.JMSConnectionTester;
import com.customized.tools.po.ToolsClassLoader;
import com.customized.tools.po.ToolsSubsystem;
import com.customized.tools.po.version.Version;
import com.customized.tools.searcher.FileSearcher;
import com.customized.tools.smartanalyser.SmartAnalyser;


public class Container extends TreeInputConsole implements LifeCycle {
	
	private final static Logger logger = Logger.getLogger(Container.class);
	
	private String mainConf;
	
	private Configuration configuration;
		
	private Status status = Status.STOP ;

	public Container(String mainConf) {
		super("CustomizedTools");
		this.mainConf = mainConf;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void doInit() {

		configuration = new Configuration(mainConf);
		
		logger.info("Initialized Configuration, name = " + configuration.getContext().getName() + ", version = " + configuration.getContext().getVersion());
	
		for(ToolsSubsystem system : configuration.getSubsystem()) {
			TreeNode node = new TreeNode(system.getName(), "<" + system.getPrompt() + ">", null, null);
			addTreeNode(node);
		}
		
		loadExternalJar();
		
		setStatus(Status.INIT);
	}

	private void loadExternalJar() {

		logger.info("load external Jars");
		
		ToolsClassLoader loader = configuration.getToolsClassLoader();
		
		String libPath = loader.getPath();
		String libUrl = loader.getUrl();
		
		if(libPath.equals("lib")){
			libPath = System.getProperty("cst.home") + File.separator + libPath;
		} 
		
		ToolsURLClassLoader classLoader = new ToolsURLClassLoader(Container.class.getClassLoader());
		classLoader.loadDependencyJars(libPath);
	}

	public void doStart() {
		
		if(status == Status.DESTORY || status == Status.STOP) {
			doInit();
		}

		try {
			prompt(Version.VERSION_STRING + " Started");
			start();
		} catch (Exception e) {
			throw new ToolsStartException("", e);
		}
		
		setStatus(Status.START);
	}

	public void doStop() {

		setStatus(Status.STOP);
	}

	public void status() {

	}

	public void destory() {

		setStatus(Status.DESTORY);
	}
	
	enum Status {
		STOP,
		INIT,
		START,
		DESTORY,
	}
	
	Map<String, String> map = new HashMap<String, String>();

	protected void handleLS(String pointer) {
		
		String[] array = pointer.split(" ");
		
		boolean isPrintDetail = false;
		
		for (int i = 0 ; i < array.length ; i ++) {
			if(array[i].equals("-l")) {
				isPrintDetail = true ;
			}
		}
		
		println(getPrintContent(isPrintDetail));
	}

	private Object getPrintContent(boolean isPrintDetail) {
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0 ; i < getCurrentNode().getSons().size() ; i ++) {
			sb.append(TAB);
			String key = String.valueOf(i + 1);
			map.put(key, getCurrentNode().getSons().get(i).getName());
			String pre = key + ". ";
			if(isPrintDetail) {
				sb.append(pre + getCurrentNode().getSons().get(i).getName() + "   " + getCurrentNode().getSons().get(i).getContent());
			} else {
				sb.append(pre + getCurrentNode().getSons().get(i).getName());
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	protected void handlePWD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleCD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleRM(String pointer) {
		handleHELP(pointer);
	}

	protected void handleADD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleHELP(String pointer) {
		println("[ls] list all tools");
		println("[ls -l] list all tools with functionality description");
		println("Choose " + order(map.keySet()) +"\n" + getPrintContent(false) + "to execute");
	}

	private String order(Set<String> keySet) {
		List<String> list = new ArrayList<String>();
		for(String key : keySet) {
			list.add(key);
		}
		Collections.sort(list);
		return list.toString();
	}
	
	static final String jarClassSearcher = "jarClassSearcher";
	static final String fileSearcher = "fileSearcher";
	static final String fileChangeMonitor = "fileChangeMonitor";
	static final String dbConnectionTester = "dbConnectionTester";
	static final String jmsConnectionTester = "jmsConnectionTester";
	static final String smartAnalyser = "smartAnalyser";
	static final String samurai = "samurai";
	static final String GCViewer = "GCViewer";

	Map<String, Object> cache = new HashMap<String, Object>();
	
	protected void handleOther(String pointer) {
		
		if(map.keySet().contains(pointer) && isSelecting(pointer)) {
			
			Object obj = cache.get(map.get(pointer));
			
			if(map.get(pointer).equals(jarClassSearcher)){
				startJarClassSearcher(obj);
			} else if(map.get(pointer).equals(fileSearcher)){
				startFileSearcher(obj);
			} else if(map.get(pointer).equals(fileChangeMonitor)){
				startFileChangeMonitor(obj);
			} else if(map.get(pointer).equals(dbConnectionTester)){
				startDbConnectionTester(obj);
			} else if(map.get(pointer).equals(jmsConnectionTester)){
				startJmsConnectionTester(obj);
			} else if(map.get(pointer).equals(smartAnalyser)){
				startSmartAnalyser(obj);
			} else if(map.get(pointer).equals(samurai)){
				startSamurai(obj);
			} else if(map.get(pointer).equals(GCViewer)){
				startGCViewer(obj);
			} 
		}
		
		handleHELP(pointer);
	}

	private void startGCViewer(Object obj) {

		GCViewerWrapper tool = null ;
		
		if(null == obj) {
			tool = new GCViewerWrapper(this);
			cache.put(GCViewer, obj);
		} else {
			tool = (GCViewerWrapper) obj ;
		}
		
		tool.execute();
	}

	private void startSamurai(Object obj) {
		
		SamuraiWrapper tool = null;
		
		if(null == obj) {
			tool = new SamuraiWrapper(this);
			cache.put(samurai, tool);
		} else {
			tool = (SamuraiWrapper) obj ;
		}
		
		tool.execute();
	}

	private void startSmartAnalyser(Object obj) {

		SmartAnalyser tool = null;
		
		if(null == obj) {
			tool = new SmartAnalyser(configuration.getAnalyser(), this);
			cache.put(smartAnalyser, tool);
		} else {
			tool = (SmartAnalyser) obj ;
		}
		
		tool.execute() ;
	}

	private void startJmsConnectionTester(Object obj) {

		JMSConnectionTester tool = null ;
		
		if(null == obj) {
			tool = new JMSConnectionTester(configuration.getJmsTester(), new WizardConsole());
			cache.put(jmsConnectionTester, tool);
		} else {
			tool = (JMSConnectionTester) obj;
		}
		
		tool.execute();
	}

	private void startDbConnectionTester(Object obj) {

		DBConnectionTester tool = null ;
		
		if(null == obj) {
			tool = new DBConnectionTester(configuration.getDbTester(), new WizardConsole());
			cache.put(dbConnectionTester, tool);
		} else {
			tool = (DBConnectionTester) obj ;
		}
		
		tool.execute();
	}

	private void startFileChangeMonitor(Object obj) {
		
		FileChangeMonitor tool = null;
		
		if(null == obj) {
			tool = new FileChangeMonitor(configuration.getFileChangeMonitor(), this);
			cache.put(fileChangeMonitor, tool);
		} else {
			tool = (FileChangeMonitor) obj ;
		}
		
		tool.execute();
	}

	private void startFileSearcher(Object obj) {
		
		FileSearcher tool = null;
		
		if(null == obj) {
			tool = new FileSearcher(configuration.getFileSearcher(), this);
			cache.put(fileSearcher, tool);
		} else {
			tool = (FileSearcher) obj;
		}
		
		tool.execute();
	}

	private void startJarClassSearcher(Object obj) {
		
		JarClassSearcher tool = null;
		
		if(null == obj) {
			tool = new JarClassSearcher(configuration.getJarClassSearcher(), this);
			cache.put(jarClassSearcher, tool);
		}  else {
			tool = (JarClassSearcher) obj ;
		}
		
		tool.execute();
	}

}
