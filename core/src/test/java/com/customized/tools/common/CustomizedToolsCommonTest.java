package com.customized.tools.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.customized.tools.TestUtil;

public class CustomizedToolsCommonTest {
	
	@Test
	public void testloadClassPathProps() throws IOException {
		Map<?, ?> map = IOUtil.loadClassPathProps(this.getClass().getClassLoader(), "tools.properties");
		TestUtil.println(map);
		assertNotNull(map);
		map = IOUtil.loadClassPathProps(Thread.currentThread().getContextClassLoader(), "tools.properties");
		TestUtil.println(map);
		assertNotNull(map);
	}
	
	@Test
	public void testConfiguration() {
		Configuration configuration = new Configuration("src/test/resources/CustomizedToolsContext.xml");
		assertEquals("dbConnectionTester", configuration.getDbTester().getId());
		assertEquals("fileChangeMonitor", configuration.getFileChangeMonitor().getId());
		assertEquals("fileSearcher", configuration.getFileSearcher().getId());
		assertEquals("GCViewer", configuration.getGcViewer().getId());
		assertEquals("jarClassSearcher", configuration.getJarClassSearcher().getId());
		assertEquals("TDA", configuration.getTda().getId());
		assertEquals("TDA", configuration.getTda().getId());
		
		assertEquals(6, configuration.getSubsystem().size());
	}
	
	@Test
	public void testConfigurationGenerater() throws JAXBException, IOException{
		String path = new File("target").getAbsolutePath();
		ToolsContentGenerater.generate(path);
	}

}
