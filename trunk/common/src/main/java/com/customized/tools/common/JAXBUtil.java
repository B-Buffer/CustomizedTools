package com.customized.tools.common;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.customized.tools.po.CustomizedToolsContext;

public class JAXBUtil {

	private static JAXBUtil instance;
	
	private JAXBUtil() {

	}
	
	public static JAXBUtil getInstance() {

		if (null == instance) {
			instance = new JAXBUtil();
		}

		return instance;
	}
	
	private JAXBContext context;
	
	private Marshaller marshaller;
	
	private Unmarshaller unmarshaller;

	public JAXBContext getContext() throws JAXBException {
		
		if(null == context) {
			context = JAXBContext.newInstance(CustomizedToolsContext.class);
		}
		
		return context;
	}

	public Marshaller getMarshaller() throws JAXBException {
		
		if(null == marshaller) {
			marshaller = getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		}
		
		return marshaller;
	}

	public Unmarshaller getUnmarshaller() throws JAXBException {
		
		if(null == unmarshaller) {
			unmarshaller = getContext().createUnmarshaller();
		}
		
		return unmarshaller;
	}
	
}
