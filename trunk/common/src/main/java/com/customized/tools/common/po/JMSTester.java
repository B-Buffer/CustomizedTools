package com.customized.tools.common.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "jmsTester")
@XmlType(propOrder = { "factoryJNDIName", "factoryClassName", "url", "pkgs", "principle", "credentials" })
public class JMSTester {

	private String factoryJNDIName;
	
	private String factoryClassName;
	
	private String url;
	
	private String pkgs;
	
	private String principle;
	
	private String credentials;

	@XmlElement(name = "factoryJNDIName")
	public String getFactoryJNDIName() {
		return factoryJNDIName;
	}

	public void setFactoryJNDIName(String factoryJNDIName) {
		this.factoryJNDIName = factoryJNDIName;
	}

	@XmlElement(name = "factoryClassName")
	public String getFactoryClassName() {
		return factoryClassName;
	}

	public void setFactoryClassName(String factoryClassName) {
		this.factoryClassName = factoryClassName;
	}

	@XmlElement(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement(name = "pkgs")
	public String getPkgs() {
		return pkgs;
	}

	public void setPkgs(String pkgs) {
		this.pkgs = pkgs;
	}

	@XmlElement(name = "principle")
	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	@XmlElement(name = "credentials")
	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "JMSTester [factoryJNDIName=" + factoryJNDIName
				+ ", factoryClassName=" + factoryClassName + ", url=" + url
				+ ", pkgs=" + pkgs + ", principle=" + principle
				+ ", credentials=" + credentials + "]";
	}
}
