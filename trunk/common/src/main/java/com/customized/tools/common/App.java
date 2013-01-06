package com.customized.tools.common;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.customized.tools.po.Analyser;
import com.customized.tools.po.ToolsContent;

public class App {

	public static void main(String[] args) throws JAXBException {
		
		ToolsContent content = new ToolsContent();
		
		Analyser analyser = new Analyser();
		analyser.setPath("/home/kylin/work/test");
		content.setAnalyser(analyser);
		
		JAXBContext context = JAXBContext.newInstance(ToolsContent.class);
		Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    m.marshal(content, System.out);
	}
}
