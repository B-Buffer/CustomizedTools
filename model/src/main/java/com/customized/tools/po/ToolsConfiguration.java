package com.customized.tools.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configuration")
public class ToolsConfiguration {

	private Analyser analyser;
	
	private JMSTester jmsTester;
	
	private DBTester dbTester;
	
	private ClassSearcher jarClassSearcher;
	
	private Searcher fileSearcher;
	
	private Monitor fileChangeMonitor;

	@XmlElement(name = "analyser")
	public Analyser getAnalyser() {
		return analyser;
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}

	@XmlElement(name = "jmsTester")
	public JMSTester getJmsTester() {
		return jmsTester;
	}

	public void setJmsTester(JMSTester jmsTester) {
		this.jmsTester = jmsTester;
	}

	@XmlElement(name = "dbTester")
	public DBTester getDbTester() {
		return dbTester;
	}

	public void setDbTester(DBTester dbTester) {
		this.dbTester = dbTester;
	}

	@XmlElement(name = "jarClassSearcher")
	public ClassSearcher getJarClassSearcher() {
		return jarClassSearcher;
	}

	public void setJarClassSearcher(ClassSearcher jarClassSearcher) {
		this.jarClassSearcher = jarClassSearcher;
	}

	@XmlElement(name = "fileSearcher")
	public Searcher getFileSearcher() {
		return fileSearcher;
	}

	public void setFileSearcher(Searcher fileSearcher) {
		this.fileSearcher = fileSearcher;
	}

	@XmlElement(name = "fileChangeMonitor")
	public Monitor getFileChangeMonitor() {
		return fileChangeMonitor;
	}

	public void setFileChangeMonitor(Monitor fileChangeMonitor) {
		this.fileChangeMonitor = fileChangeMonitor;
	}
}
