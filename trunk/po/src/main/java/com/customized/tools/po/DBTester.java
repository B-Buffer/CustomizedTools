package com.customized.tools.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "dbTester")
@XmlType(propOrder = { "driver", "url", "username", "password", "driverlib"})
public class DBTester {

	private String driver;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String driverlib;

	@XmlElement(name = "driver")
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@XmlElement(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "driverlib")
	public String getDriverlib() {
		return driverlib;
	}

	public void setDriverlib(String driverlib) {
		this.driverlib = driverlib;
	}

	@Override
	public String toString() {
		return "driver=" + driver + ", url=" + url + ", username=" + username + ", password=****** ";
	}
}
