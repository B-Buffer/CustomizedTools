package com.customized.tools.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "analyser")
public class Analyser extends Entity {

	private static final long serialVersionUID = 3949882236046489505L;
	
	private String path;

	@XmlElement(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Analyser [path=" + path + "]";
	}

	
}
