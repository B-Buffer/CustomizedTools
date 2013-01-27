package com.customized.tools.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;
import com.customized.tools.common.Configuration;
import com.customized.tools.jarClassSearcher.JarClassSearcher;
import com.customized.tools.po.ToolsSubsystem;


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
		
		setStatus(Status.INIT);
	}

	public void doStart() {
		
		if(status == Status.DESTORY || status == Status.STOP) {
			doInit();
		}

		try {
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
	
	String jarClassSearcher = "jarClassSearcher";
	String fileSearcher = "fileSearcher";
	String fileChangeMonitor = "fileChangeMonitor";
	String dbConnectionTester = "dbConnectionTester";
	String jmsConnectionTester = "jmsConnectionTester";
	String smartAnalyser = "smartAnalyser";
	String samurai = "samurai";
	String GCViewer = "GCViewer";

	Map<String, Object> cache = new HashMap<String, Object>();
	
	protected void handleOther(String pointer) {
		
		if(map.keySet().contains(pointer) && isSelecting(pointer)) {
			
			Object obj = cache.get(map.get(pointer));
			
			if(map.get(pointer).equals(jarClassSearcher)){
				startJarClassSearcher(obj);
			} else if(map.get(pointer).equals(fileSearcher)){
				
			} else if(map.get(pointer).equals(fileChangeMonitor)){
				
			} else if(map.get(pointer).equals(dbConnectionTester)){
				
			} else if(map.get(pointer).equals(jmsConnectionTester)){
				
			} else if(map.get(pointer).equals(smartAnalyser)){
				
			} else if(map.get(pointer).equals(samurai)){
				
			} else if(map.get(pointer).equals(GCViewer)){
				
			} 
		}
		
		handleHELP(pointer);
	}

	private void startJarClassSearcher(Object obj) {
		
		JarClassSearcher tool = null;
		if(null == obj) {
			tool = new JarClassSearcher(configuration.getJarClassSearcher(), this);
			cache.put("jarClassSearcher", tool);
		}  else {
			tool = (JarClassSearcher) obj ;
		}
		
		tool.execute();
	}

}
