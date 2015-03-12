package com.customized.tools.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configuration")
public class ToolsConfiguration {

	private GCViewerEntity gcViewer;
	
	private TDAEntity tda;
	
	private DBTester dbTester;
	
	private ClassSearcher jarClassSearcher;
	
	private Searcher fileSearcher;
	
	private Monitor fileChangeMonitor;

	@XmlElement(name = "gcViewer")
	public GCViewerEntity getGcViewer() {
		return gcViewer;
	}

	public void setGcViewer(GCViewerEntity gcViewer) {
		this.gcViewer = gcViewer;
	}
	
	@XmlElement(name = "tda")
	public TDAEntity getTda() {
		return tda;
	}

	public void setTda(TDAEntity tda) {
		this.tda = tda;
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
