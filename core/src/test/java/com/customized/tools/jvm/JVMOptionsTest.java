package com.customized.tools.jvm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customized.tools.model.JVMOptions;

public class JVMOptionsTest {
	
	@Test
	public void testJVMOptions(){
		JVMOptions opt = new JVMOptions();
		
		opt.setVendor("Oracle");
		opt.setCollector("CMS");
		opt.setHeapSize(2048);
		opt.setEnableLargePage(Boolean.TRUE);
		opt.setEnableGCLogging(Boolean.TRUE);
		opt.setEnableAggressiveOpts(Boolean.TRUE);
		
		String expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseParNewGC -XX:+ExplicitGCInvokesConcurrent -XX:CMSInitiatingOccupancyFraction=80 -XX:CMSIncrementalSafetyFactor=20 -XX:+UseCMSInitiatingOccupancyOnly -XX:+UseLargePages -Xmx2048M -Xms2048M -verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setCollector("Parallel");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:+UseLargePages -Xmx2048M -Xms2048M -verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setCollector("G1");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=500 -XX:+UseLargePages -Xmx2048M -Xms2048M -verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setVendor("OpenJDK");
		opt.setCollector("CMS");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseParNewGC -XX:+ExplicitGCInvokesConcurrent -XX:CMSInitiatingOccupancyFraction=80 -XX:CMSIncrementalSafetyFactor=20 -XX:+UseCMSInitiatingOccupancyOnly -XX:+UseLargePages -Xmx2048M -Xms2048M -verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setVendor("OpenJDK");
		opt.setCollector("Parallel");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:+UseLargePages -Xmx2048M -Xms2048M -verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setVendor("OpenJDK");
		opt.setCollector("G1");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=500 -XX:+UseLargePages -Xmx2048M -Xms2048M -verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setVendor("IBM");
		opt.setCollector("CMS");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseParNewGC -XX:+ExplicitGCInvokesConcurrent -XX:CMSInitiatingOccupancyFraction=80 -XX:CMSIncrementalSafetyFactor=20 -XX:+UseCMSInitiatingOccupancyOnly -Xlp -Xmx2048M -Xms2048M -verbose:gc -Xverbosegclog:gc.log.`date +%Y%m%d%H%M%S` -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setVendor("IBM");
		opt.setCollector("Parallel");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseParallelGC -XX:+UseParallelOldGC -Xlp -Xmx2048M -Xms2048M -verbose:gc -Xverbosegclog:gc.log.`date +%Y%m%d%H%M%S` -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		opt.setVendor("IBM");
		opt.setCollector("G1");
		expected = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=500 -Xlp -Xmx2048M -Xms2048M -verbose:gc -Xverbosegclog:gc.log.`date +%Y%m%d%H%M%S` -XX:+AggressiveOpts";
		assertEquals(expected, opt.toString());
		
		
	}

}
