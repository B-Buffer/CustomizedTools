package com.customized.tools.common.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jarClassSearcher")
public class ClassSearcher {

	private String className;
	
	private String folderPath;

	@XmlElement(name = "className")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
		return "ClassSearcher [className=" + className + ", folderPath="
				+ folderPath + "]";
	}
}
