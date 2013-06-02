package com.customized.tools.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashSet;
import java.util.Set;

import com.customized.tools.cli.InputConsole;

public class Main {
	
	final static String baseDir = System.getProperty("cst.home");
	
	final static String libDir = baseDir +  File.separator + "lib" ;
	
	final static String installDir = baseDir +  File.separator + "configuration" + File.separator + "install";
	
	final static String jmsDir = baseDir + File.separator + "modules" + File.separator + "jms-client" + File.separator + "main" ;
	
	final static String jmsModule = jmsDir + File.separator + "module.xml" ;
	
	final static String jmsInstallModule = installDir + File.separator + "jms" + File.separator + "module.xml" ;
	
	final static String dbDir = baseDir + File.separator + "modules" + File.separator + "db-driver" + File.separator + "main" ;

	final static String dbModule = dbDir + File.separator + "module.xml" ;
	
	final static String dbInstallModule = installDir + File.separator + "db" + File.separator + "module.xml" ;
	
	final static String STR_SIGN = "<$#%&54#%%^&>";
	
	InputConsole console = new InputConsole();
		
	public static void main(String[] args) {
		new Main().run();
		
	}
	
	private void run() {
		
		String prompt = "  [1]. install database driver\n" + 
						"  [2]. install jms server client" + "\t" + "Default [1]"; 

		int result = console.readWithDef(prompt, '1', '1', '2');

		if (result == '1') {
			installDBDriver();
		} else if(result == '2'){
			installJMSClient();
		}

	}

	private void installDBDriver() {

		String jarFolder = console.readFolderPath("please supply driver jar folder", libDir, true);
				
		Set<File> jarSet = getCopyJarSet(jarFolder);
		
		copy(new File(dbInstallModule), new File(dbModule));
		
		install(jarSet, dbDir, dbModule);
		
		console.prompt("install database driver finish");
		
	}

	private void installJMSClient() {

		String jarFolder = console.readFolderPath("please supply jms client jar folder", libDir, true);
	
		Set<File> jarSet = getCopyJarSet(jarFolder);
		
		copy(new File(jmsInstallModule), new File(jmsModule));
		
		install(jarSet, jmsDir, jmsModule);
		
		console.prompt("install jms client finish");
	}
	
	private void install(Set<File> jarSet, String dest, String module) {
		
		for(File file : jarSet) {
			String name = file.getName();
			copy(file, new File(dest + File.separator + name));
		}
		
		updateModule(module, jarSet);
	}

	//TODO--
	private void updateModule(String module, Set<File> jarSet) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<resources>\n");
		for(File file : jarSet) {
			sb.append("    <resource-root path=\"" + file.getName() + "\"/>\n");
		}
		sb.append("</resources>");
		
		try {
			StringBuffer buffer = new StringBuffer();
			File file = new File(module);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if(line.contains(STR_SIGN)) {
					line = line.replace(STR_SIGN, sb.toString());
				}
				buffer.append(line + "\n");
			}
			reader.close();

			FileWriter writer = new FileWriter(module);
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e) {
			console.prompt("install error, " + e.getMessage());
			console.prompt("please install again");
		}
	}

	private void copy(File from, File to) {
		
		FileChannel source = null;
	    FileChannel destination = null;
		
		try {
			if (to.exists())
				to.delete();

			to.createNewFile();
			
			source = new FileInputStream(from).getChannel();
			destination = new FileOutputStream(to).getChannel();
			destination.transferFrom(source, 0, source.size());
		} catch (Exception e) {
			console.prompt("install error, " + e.getMessage());
			console.prompt("please install again");
		} finally {
			try {
				if(source != null) {
				    source.close();
				}
				if(destination != null) {
				    destination.close();
				}
			} catch (IOException e) {
				
			}
		}
	}

	private Set<File> getCopyJarSet(String jarFolder) {
		
		File[] files = new File(jarFolder).listFiles();
		
		Set<File> jarSet = new HashSet<File>();
		for(File file : files) {
			if(file.getAbsolutePath().endsWith(".jar")) {
				jarSet.add(file);
			}
		}
		
		return jarSet ;
	}

}
