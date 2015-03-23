package com.customized.tools.jarClassSearcher;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;

import org.junit.Test;

import com.customized.tools.TestUtil;


public class TestJarClassSearcher {
	
	@Test
	public void testSearchClassName() {
		
		MyJarClassSearcher test = new MyJarClassSearcher();
		String expected = "com/sample/Test.class";
		
		String className = "com.sample.Test";	
		assertEquals(expected, test.buildClassName(className));
		
		className = "com.sample.Test.java";
		assertEquals(expected, test.buildClassName(className));
		
		className = "com.sample.Test.Class";
		assertEquals(expected, test.buildClassName(className));
		
		className = "com/sample/Test";
		assertEquals(expected, test.buildClassName(className));
		
		className = "com\\sample\\Test";
		assertEquals(expected, test.buildClassName(className));
	}
	
	@Test
	public void testCST05(){
		
		MyJarClassSearcher test = new MyJarClassSearcher();
		test.execute();
	}
	
	private class MyJarClassSearcher extends JarClassSearcher{

		public MyJarClassSearcher() {
			super(null, null);
		}

		@Override
		public void execute() {
			File file = new File("src/test/resources/sample");
			String className = "com/kylin/web/servlet/SessionPrintServlet";
			List<String> result = new ArrayList<String>();
			Set<JarFile> jarFileSet = new HashSet<JarFile>();
			try {
				JarFileCollection(file, jarFileSet);
				result = getResultJars(jarFileSet, className);
				TestUtil.println(result);
			} catch (IOException e) {

			}
		}
		
	}

	

}
