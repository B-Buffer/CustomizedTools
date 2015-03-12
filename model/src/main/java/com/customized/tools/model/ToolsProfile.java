package com.customized.tools.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "profile")
public class ToolsProfile implements Serializable {

	private static final long serialVersionUID = -2705817013737130502L;
	
	private List<ToolsSubsystem> subsystem ;

	@XmlElement
	public List<ToolsSubsystem> getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(List<ToolsSubsystem> subsystem) {
		this.subsystem = subsystem;
	}
}
