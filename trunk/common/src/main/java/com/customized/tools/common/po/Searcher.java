package com.customized.tools.common.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fileSearcher")
public class Searcher {

	private String fileName;
	
	private String folderPath;

	@XmlElement(name = "fileName")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@XmlElement(name = "folderPath")
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	@Override
	public String toString() {
		return "Searcher [fileName=" + fileName + ", folderPath=" + folderPath
				+ "]";
	}
}
