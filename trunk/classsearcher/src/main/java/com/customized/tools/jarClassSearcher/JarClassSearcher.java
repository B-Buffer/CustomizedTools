package com.customized.tools.jarClassSearcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.customized.tools.common.console.InputConsole;
import com.customized.tools.common.po.ClassSearcher;

/**
 * JarClassSearcher search class from a folder, even class exist in jar file
 * 
 * @author kylin
 *
 */
public class JarClassSearcher  {

	private static final Logger logger = Logger.getLogger(JarClassSearcher.class);
	
	private ClassSearcher jarClassSearcher;
	
	private InputConsole console;
	
	public JarClassSearcher(ClassSearcher jarClassSearcher, InputConsole console) {

		this.jarClassSearcher = jarClassSearcher ;
		this.console = console ;
	}

	public void execute() {
		
		logger.info("JarClassSearcher Start");
		
		String msg = "Run JarClassSearcher From Command Line\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No";
		int a = '1';
		int b = '2';
		int res = console.read(msg, a, b);
		
		try {
			if(res == a) {
				String folder = console.readFolderPath("Input jarClassSearcher folder path [</home/kylin/work/eap/jboss-eap-6.0>]", true);
				jarClassSearcher.setFolderPath(folder);
				String className = console.readFilePath("Input jarClassSearcher class name [<org.jboss.modules.Main>]", false);
				jarClassSearcher.setClassName(className);
			}
			
			String prompt = "\nSearching '" + jarClassSearcher.getClassName() + "' Under Directory " + jarClassSearcher.getFolderPath() + "\n";
			
			console.prompt(prompt);
			
			Set<JarFile> jarFileSet = new HashSet<JarFile>();		
			JarFileCollection(new File(jarClassSearcher.getFolderPath()), jarFileSet);
			
			List<String> result = getResultJars(jarFileSet, jarClassSearcher.getClassName());
			
			printToConsole(result);
		} catch (Exception e) {
			JarClassSearcherException ex = new JarClassSearcherException("JarClassSearcher return a Error", e);
			console.prompt(ex.getMessage());
			logger.error("", ex);
			throw ex;
		}
	}

	private void printToConsole(List<String> result) {

		StringBuffer sb = new StringBuffer();
		sb.append(jarClassSearcher.getClassName() + " has been found " + result.size() + " times.\n");
		for (String str : result) {
			sb.append("    " + str + "\n");
		}
		
		console.prompt(sb.toString());
		
		if(logger.isDebugEnabled()) {
			logger.debug(sb.toString());
		}
	}

	private void JarFileCollection(File jarDirs, Set<JarFile> jarFileSet) throws IOException {
		
		if (!jarDirs.isDirectory()) {
			throw new JarClassSearcherException("Incorrect directory name has passed: " + jarDirs.getAbsolutePath());
		}
		
		for (File file : jarDirs.listFiles()) {
			if (file.isDirectory()) {
				JarFileCollection(file, jarFileSet);
			} else if (file.getName().endsWith(".jar")) {
				JarFile jarFile;
				try {
					jarFile = new JarFile(file);
					jarFileSet.add(jarFile);
				} catch (Exception e) {
					String prompt = "can not create jarFile via " + file ;
					logger.error(prompt, new JarClassSearcherException(prompt, e));
					console.prompt(prompt + ", ignored");
				}
				
			}
		}
	}

	private List<String> getResultJars(Set<JarFile> jarFileSet, String className) throws IOException {
		
		List<String> results = new ArrayList<String>();
		className = buildClassName(className);
		for (JarFile jarFile : jarFileSet) {
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jar = entrys.nextElement();
				if (jar.getName().endsWith(className)) {
					results.add(jarFile.getName());
				}
			}
			jarFile.close();
		}
		return results;
	}

	private String buildClassName(String className) {
		if (className.endsWith(".java")) {
			className = className.substring(0, className.length() - 5);
		} else if (className.endsWith(".class") || className.endsWith(".Class")) {
			className = className.substring(0, className.length() - 6);
		}
		return className.replace('.', '/').replace('\\', '/') + ".class";
	}

}
