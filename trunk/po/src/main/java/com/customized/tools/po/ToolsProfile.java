package com.customized.tools.po;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "profile")
public class ToolsProfile {

	private List<ToolsSubsystem> subsystem ;

	@XmlElement
	public List<ToolsSubsystem> getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(List<ToolsSubsystem> subsystem) {
		this.subsystem = subsystem;
	}
}
