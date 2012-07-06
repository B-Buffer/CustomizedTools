package com.customized.tools;

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

import com.customized.tools.jarclasssearcher.JarClassSearcherException;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class JarClassSearcher extends AbstractTools {

	private static final Logger logger = Logger.getLogger(JarClassSearcher.class);
	
	public JarClassSearcher(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		try {
			String prompt = "\nSearching '" + props.getProperty("searcher.class", true) + "' Under Directory " + props.getProperty("searcher.class.folder", true) + "\n";

			logger.info(prompt);
			
			console.prompt(prompt);
			
			Set<JarFile> jarFileSet = new HashSet<JarFile>();		
			JarFileCollection(new File(props.getProperty("searcher.class.folder")), jarFileSet);
			
			List<String> result = getResultJars(jarFileSet, props.getProperty("searcher.class"));
			
			printToConsole(result);
		} catch (Exception e) {
			
			console.prompt("Unexpected Exception Returned, " + e.getMessage() + "\n");
			
			e.printStackTrace();
		}
	}

	private void printToConsole(List<String> result) {

		StringBuffer sb = new StringBuffer();
		sb.append(props.getProperty("searcher.class") + " has been found " + result.size() + " times.\n");
		for (String str : result) {
			sb.append("  " + str + "\n");
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
				JarFile jarFile = new JarFile(file);
				jarFileSet.add(jarFile);
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
