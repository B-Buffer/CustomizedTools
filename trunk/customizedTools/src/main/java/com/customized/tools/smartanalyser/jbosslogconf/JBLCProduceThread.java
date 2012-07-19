package com.customized.tools.smartanalyser.jbosslogconf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class JBLCProduceThread extends JBLCThreadBase implements Runnable {

	private static final Logger logger = Logger.getLogger(JBLCProduceThread.class);
	
	private Set<String> imgSet;
	
	public JBLCProduceThread(Set<String> imgSet) {
		super(null);
		this.imgSet = imgSet;
	}

	public JBLCProduceThread(Threshold threshold, Set<String> imgSet) {
		super(threshold);
		this.imgSet = imgSet;
	}
	
	public void run() {

		setActive(true);

		logThreadStatus(getName());

		try {
			for(String path : imgSet) {
				
				logger.info("Analysing " + path);
				
				if(isBootLog(path)){
					analysingBootLog(path);
				} else if(isServerLog(path)) {
					analysingServerLog(path);
				} else if(isConfFile(path)) {
					analysingConfFile(path);
				}
			}
		} catch (Exception e) {
			throw new JBossLogConfAnalyserException("Produce thread analysing log/conf file error", e);
		}
	}

	
	private void analysingServerLog(String path) throws IOException {

		FileEntity entity = new FileEntity();
		threshold.addToThresholdQueue(getName(), entity);
		entity.setPath(path);
		
		int prelengh = countPrelengh(path);
		
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String str = null;
		while((str = br.readLine()) != null) {
			
			if(isError(str, prelengh)){
				addSubString(entity, str, 1);
			}
		}
	}

	private void analysingConfFile(String path) {
		// TODO Auto-generated method stub
		
	}

	
	private void analysingBootLog(String path) throws IOException {

		FileEntity entity = new FileEntity();
		threshold.addToThresholdQueue(getName(), entity);
		entity.setPath(path);
		
		int prelengh = countPrelengh(path);
		
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String str = null;
		while((str = br.readLine()) != null) {
			
			if(str.contains("Release ID:")) {
				addSubString(entity, str, 2, "Release ID:");
			} else if(str.contains("Server Home Dir:")){
				addSubString(entity, str, 2, "Server Home Dir:");
			} else if(str.contains("OS-System:")) {
				addSubString(entity, str, 2, "OS-System:");
			} else if(str.contains("Java Runtime:")) {
				addSubString(entity, str, 2, "Java Runtime:");
			} else if(str.contains("VM arguments:")) {
				addSubString(entity, str, 2, "VM arguments:");
			} else if (isError(str, prelengh)){
				addSubString(entity, str, 1);
			}
		}
	}

	private boolean isError(String str, int prelengh) {

		if((str.contains("ERROR") && (str.indexOf("ERROR") == prelengh)) || (str.contains("FATAL") && (str.indexOf("FATAL") == prelengh))) {
			return true;
		}
		
		return false;
	}

	private int countPrelengh(String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String str = null;
		Cursor a = new Cursor() ;
		Cursor b = new Cursor() ;
	
		int result = 0;
		
		while((str = br.readLine()) != null) {
			
			if(a.isSet() && b.isSet() ) {
				if(a.getValue() == b.getValue()){
					result = a.getValue();
					break;
				} else {
					a.setSet(false);
					b.setSet(false);
					a.setValue(-1);
					b.setValue(-1);
				}
			} 
			
			if(str.contains("INFO")) {
				initAB(a, b, str, "INFO");
			} else if(str.contains("DEBUG")) {
				initAB(a, b, str, "DEBUG");
			} else if (str.contains("ERROR")) {
				initAB(a, b, str, "ERROR");
			} else if(str.contains("FATAL")) {
				initAB(a, b, str, "FATAL");
			} else if(str.contains("TRACE")) {
				initAB(a, b, str, "TRACE");
			}
		}
		
		br.close();
		
		return result;
	}

	private void initAB(Cursor a, Cursor b, String str, String level) {

		if(a.isSet()) {
			b.setValue(str.indexOf(level));
			b.setSet(true);
		} else {
			a.setValue(str.indexOf(level));
			a.setSet(true);
		}
	}

	private void addSubString(FileEntity entity, String str, int level) {

		JBossLogConfEntity jbcl = new JBossLogConfEntity();
		entity.add(jbcl);
		jbcl.setLevel(level);
		jbcl.setContext(str);
	}

	private void addSubString(FileEntity entity, String str, int level, String start) {

		JBossLogConfEntity jbcl = new JBossLogConfEntity();
		entity.add(jbcl);
		jbcl.setLevel(level);
		jbcl.setContext(str.substring(str.indexOf(start)));
	}

	
	private boolean isConfFile(String path) {
		return path.contains("ds.xml");
	}

	private boolean isServerLog(String path) {
		return path.contains("server.log");
	}

	private boolean isBootLog(String path) {
		return path.contains("boot.log");
	}

	public String getName() {
		return JBLCProduceThread.class.getSimpleName();
	}
	
	public static void main(String[] args) throws IOException {
		
		Set<String> imgSet = new HashSet<String>();
		imgSet.add("/home/kylin/work/tmp/jboss-eap-5.1/jboss-as/server/default/log/boot.log");
		imgSet.add("/home/kylin/work/tmp/jboss-eap-5.1/jboss-as/server/default/log/server.log");
		
		JBLCProduceThread produce = new JBLCProduceThread(new Threshold(), imgSet);
		
//		System.out.println(produce.countPrelengh("/home/kylin/work/tmp/jboss-eap-5.1/jboss-as/server/default/log/boot.log"));
		
		produce.run();
		test(produce.threshold.getFromThresholdQueue(""));
		test(produce.threshold.getFromThresholdQueue(""));
	}

	private static void test(FileEntity entity) {
		for(JBossLogConfEntity jblc : entity.getEntity()){
			System.out.println(jblc.getContext());
		}
	}

}
