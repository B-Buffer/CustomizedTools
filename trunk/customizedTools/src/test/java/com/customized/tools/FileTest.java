package com.customized.tools;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FileTest {

	public static void main(String[] args) {
		
		File file = new File("/home/kylin/work/tmp/655258/jboss-eap-5.0/jboss-as/server/default/log/BK/20120410/test.zip");
		
		Set<String> imgSet = new HashSet<String>();
		imgSet.add(file.getAbsolutePath());
		
		for(Iterator iterator = imgSet.iterator(); iterator.hasNext();) {
			String path =  (String) iterator.next();
			
			System.out.println(path);
		}
		
		System.out.println(file.getParentFile());
	}

}
