package com.customized.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import com.customized.tools.startup.ToolsClassLoader;

public class SamuraiTest {

	public static void main(String[] args) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		
		String clsName = "samurai.swing.Samurai";
		
		String[] strArray = new String[0];
		
		ToolsClassLoader loader = new ToolsClassLoader();
		
		loader.loadexternalJar("./lib/jars/samurai.jar");
		
		Thread.currentThread().setContextClassLoader(loader);
		
		Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(clsName);
	
		Method method = cls.getMethod("main", new Class[]{strArray.getClass()});
		
		method.invoke(cls.newInstance(), new Object[]{strArray});
		
		System.out.println(method);
	}

}
