package com.customized.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;
import com.customized.tools.common.Configuration;
import com.customized.tools.model.Entity;
import com.customized.tools.model.ToolsSubsystem;
import com.customized.tools.model.version.Version;


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
			TreeNode node = new TreeNode(system.getName(), "'" + system.getPrompt() + "'", null, null);
			addTreeNode(node);
		}
		
		setStatus(Status.INIT);
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
			sb.append(twoTab());
			String key = String.valueOf(i + 1);
			map.put(key, getCurrentNode().getSons().get(i).getName());
			String pre = key + ". ";
			if(isPrintDetail) {
				sb.append(pre + getCurrentNode().getSons().get(i).getName() + "   " + getCurrentNode().getSons().get(i).getContent());
			} else {
				sb.append(pre + getCurrentNode().getSons().get(i).getName());
			}
			sb.append(ln());
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
		println("");
		println(tab() + "[ls] list all tools");
		println(tab() + "[ls -l] list all tools with functionality description");
		println(tab() + "Choose digit 1 - " + configuration.getSubsystem().size() + ln() + getPrintContent(false) + tab() + "to start" + ln());
	}

	protected String order(Set<String> keySet) {
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
	static final String tda = "TDA";
	static final String GCViewer = "GCViewer";
	
	Map<String, AbstractTools> objCache = new HashMap<>();
	
	protected void handleOther(String pointer) {
		
		if(map.keySet().contains(pointer)) {
			
			AbstractTools tool = null;

			
			switch(map.get(pointer)){
			case jarClassSearcher:
				tool = initTools(configuration.getJarClassSearcher(), "com.customized.tools.jarClassSearcher.JarClassSearcher", jarClassSearcher);
				break;
			case fileSearcher:
				tool = initTools(configuration.getFileSearcher(), "com.customized.tools.searcher.FileSearcher", fileSearcher);
				break;
			case fileChangeMonitor:
				tool = initTools(configuration.getFileChangeMonitor(), "com.customized.tools.filechangemonitor.FileChangeMonitor", fileChangeMonitor);
				break;
			case dbConnectionTester:
				tool = initTools(configuration.getDbTester(), "com.customized.tools.dbtester.DBConnectionTester", dbConnectionTester);
				break;
			case tda:
				tool = initTools(configuration.getTda(), "com.customized.tools.tda.TDAWrapper", tda);
				break;
			case GCViewer:
				tool = initTools(configuration.getGcViewer(), "com.customized.tools.gcviewer.GCViewerWrapper", GCViewer);
				break;
			default:
				break;
			}
			
			tool.execute();
		} else {
			handleHELP(pointer);
		}
		
	}

	private AbstractTools initTools(Entity entity, String className, String key) {
		
		AbstractTools tool = objCache.get(key);
		
		if(null != tool){
			return tool;
		}
		
		try {
			Class<?> classDefinition = this.getClass().getClassLoader().loadClass(className);
			tool = (AbstractTools) classDefinition.getConstructor(new Class[]{Entity.class, InputConsole.class}).newInstance(new Object[]{entity, this});
			objCache.put(key, tool);
			return tool;
		} catch (Exception e) {
			throw new ToolsStartException("Init tools failed", e);
		}
	}


}
