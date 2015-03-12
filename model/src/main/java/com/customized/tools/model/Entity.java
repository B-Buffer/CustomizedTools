package com.customized.tools.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Entity implements Serializable {

	private static final long serialVersionUID = -1479510289888593457L;
	
	protected String id;

	@XmlAttribute(name = "id", required = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
