package com.customized.tools.po;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "https://github.com/kylinsoong/CustomizedTools")
public class ToolsContent {

	private Analyser analyser;

	@XmlElement(name = "analyser")
	public Analyser getAnalyser() {
		return analyser;
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}
}
