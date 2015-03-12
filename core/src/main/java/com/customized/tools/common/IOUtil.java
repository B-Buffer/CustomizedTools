package com.customized.tools.common;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class IOUtil {

	public static void closeStream(Closeable stream) {
		try {
			stream.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(stream + " can not close", e);
		}
	}
	
	public static Map<?,?> loadClassPathProps(Class<?> cls, String props) throws IOException{
		final Properties properties = new Properties();
		properties.load(cls.getResourceAsStream(props));
		return properties;
	}
	
	public static Map<?,?> loadClassPathProps(ClassLoader loader, String props) throws IOException{
		final Properties properties = new Properties();
		properties.load(loader.getResourceAsStream(props));
		return properties;
	}

}