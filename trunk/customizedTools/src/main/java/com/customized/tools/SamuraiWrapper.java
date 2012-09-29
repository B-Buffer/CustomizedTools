package com.customized.tools;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.customized.tools.samurai.SamuraiException;
import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public class SamuraiWrapper extends AbstractTools{

	private static final Logger logger = Logger.getLogger(SamuraiWrapper.class);
	
	public SamuraiWrapper(ToolsProperties props, ToolsConsole console) {
		super(props, console);
	}

	public void execute() throws Throwable {
		
		logger.info("SamuraiWrapper start Samurai");
		
		console.prompt("SamuraiWrapper start Samurai");
		
		String clsName = "samurai.swing.Samurai";
		
		String[] strArray = new String[0];
		
		try {
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(clsName);
			
			Method method = cls.getMethod("main", new Class[]{strArray.getClass()});
			
			method.invoke(cls.newInstance(), new Object[]{strArray});
		} catch (Throwable t) {
			
			SamuraiException ex = new SamuraiException("SamuraiWrapper return a Error", t);
			
			logger.equals(ex);
			
			console.prompt("Samurai return a error");
			
			throw ex;
		} finally {
			
		}
	}

}
