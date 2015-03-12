package com.customized.tools.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gcViewer")
public class GCViewerEntity extends Entity {

	private static final long serialVersionUID = -4055181623049821767L;

	private String path;
	
	private String name;

	@XmlElement(name = "gcLogPath")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@XmlElement(name = "exportFileName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GCViewerEntity [path=" + path + ", name=" + name + "]";
	}

}
