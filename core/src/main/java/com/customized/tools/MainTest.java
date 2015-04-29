package com.customized.tools;

/**
 * 
 *  mvn clean install dependency:copy-dependencies
 *  
 *  java -cp target/dependency/*:target/cst-core-3.0-SNAPSHOT.jar com.customized.tools.MainTest
 *  
 *  -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=y
 * 
 * 
 * @author kylin
 *
 */
public class MainTest {

	public static void main(String[] args) {
		
		AeshContainer container = new AeshContainer();

		container.doInit();
		
		container.doStart();
	}

}
