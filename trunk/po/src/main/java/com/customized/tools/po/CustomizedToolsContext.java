package com.customized.tools.po;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.customized.tools.po.version.Version;

@XmlRootElement(namespace = "https://github.com/kylinsoong/CustomizedTools")
@XmlType(propOrder = { "configuration", "profile" })
public class CustomizedToolsContext {
	
	private String version = Version.version();
	
	private String name = Version.name();
	
	private ToolsConfiguration configuration ;
	
	private ToolsProfile profile ;
	
	
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

	@XmlElement
	public ToolsConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ToolsConfiguration configuration) {
		this.configuration = configuration;
	}

	@XmlElement
	public ToolsProfile getProfile() {
		return profile;
	}

	public void setProfile(ToolsProfile profile) {
		this.profile = profile;
	}


}
