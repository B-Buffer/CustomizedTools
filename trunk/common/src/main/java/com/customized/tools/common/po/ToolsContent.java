package com.customized.tools.common.po;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.customized.tools.common.Version;

@XmlRootElement(namespace = "https://github.com/kylinsoong/CustomizedTools")
public class ToolsContent {
	
	private String version = Version.version();
	
	private String name = Version.name();

	private Analyser analyser;
	
	private JMSTester jmsTester;
	
	private DBTester dbTester;
	
	private ClassSearcher jarClassSearcher;
	
	private Searcher fileSearcher;
	
	private Monitor fileChangeMonitor;

	@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
