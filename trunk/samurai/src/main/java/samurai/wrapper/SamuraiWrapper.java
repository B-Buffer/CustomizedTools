package samurai.wrapper;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.customized.tools.cli.Console;

public class SamuraiWrapper {
	
	private static final Logger logger = Logger.getLogger(SamuraiWrapper.class);
	
	private Console console;
	
	public SamuraiWrapper(Console console) {
		this.console = console ;
	}
	
	public void execute() {
		
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
			console.prompt("Samurai return a error, " + ex.getMessage());
			throw ex;
		} 
	}

}
