package com.customized.tools.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fileChangeMonitor")
public class Monitor {

	private String folderPath;
	
	private String resultFile;

	@XmlElement(name = "folderPath")
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	@XmlElement(name = "resultFile")
	public String getResultFile() {
		return resultFile;
	}

	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}

	@Override
	public String toString() {
		return "Monitor [folderPath=" + folderPath + ", resultFile="
				+ resultFile + "]";
	}
}
