package com.customized.tools.startup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class ToolsProperties extends Properties {

	private static final Logger logger = Logger.getLogger(ToolsProperties.class);
	
	private static final long serialVersionUID = -4468233579411633293L;
	
	public void debugPropsInfo() {
		logger.debug("------------------");
		if (logger.isDebugEnabled()) {
			logger.debug("Tools properties info:");
			String key = null;
			Iterator iterator = keySet().iterator();
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				logger.debug(" " + key + "=" + getProperty(key));
			}
		}
		logger.debug("------------------");
	}
	
	public String getProperty(String key, boolean checkEmpty) {
		String val = getProperty(key);
		if (checkEmpty && (null == val || val.length() == 0)) {
			throw new ToolsPropertyException(key + " does not fond in Tools.xml");
		}

		return val;
	}

	public String getPropertyNoNull(String key) {
		return getProperty(key, "");
	}

	public String[] splitProperty(String key, String splitStr, boolean checkEmpty) {
		String val = getProperty(key, checkEmpty);
		
		if(!checkEmpty && ( null==val || val.length()==0) ) {
			return new String[0];
		}
		
		String[] str = val.split(splitStr);
		
		//filter empty and avoid duplicated.
		Set<String> strResult=  new HashSet<String>();
		for (int i = str.length ; --i >= 0 ;) {
			String s = str[i].trim();
			if(s.length()>0)
				strResult.add(s);
		}
		return strResult.toArray(new String[strResult.size()]);
	}	

}
