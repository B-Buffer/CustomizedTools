package com.customized.tools.po;

import javax.naming.Context;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "jmsTester")
@XmlType(propOrder = { "factoryJNDIName", "factoryClassName", "url", "pkgs", "principle", "credentials", "deplibraries"})
public class JMSTester {

	private String factoryJNDIName;
	
	private String factoryClassName;
	
	private String url;
	
	private String pkgs;
	
	private String principle;
	
	private String credentials;
	
	private String deplibraries;

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

	@XmlElement(name = "deplibraries")
	public String getDeplibraries() {
		return deplibraries;
	}

	public void setDeplibraries(String deplibraries) {
		this.deplibraries = deplibraries;
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("        " + Context.INITIAL_CONTEXT_FACTORY + ": " + factoryClassName + "\n");
		sb.append("        " + Context.PROVIDER_URL + ": " + url + "\n");
		sb.append("        " + Context.URL_PKG_PREFIXES + ": " + pkgs + "\n");
		sb.append("        " + Context.SECURITY_PRINCIPAL + ": " + principle + "\n");
		sb.append("        " + Context.SECURITY_CREDENTIALS + ": " + credentials);
		
		return sb.toString();
	}
}
