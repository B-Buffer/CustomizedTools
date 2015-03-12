package com.customized.tools.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tda")
public class TDAEntity extends Entity {

	private static final long serialVersionUID = -5862482973344887134L;
	
	private String path;

	@XmlElement(name = "tdumpPath")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "TDAEntity [path=" + path + "]";
	}

}
