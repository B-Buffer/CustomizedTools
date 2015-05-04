package com.customized.tools.jvm;

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
		System.out.println(opt);
		
		opt.setCollector("Parallel");
		System.out.println(opt);
		
		opt.setCollector("G1");
		System.out.println(opt);
		
		opt.setVendor("OpenJDK");
		opt.setCollector("CMS");
		System.out.println(opt);
		
		opt.setVendor("OpenJDK");
		opt.setCollector("Parallel");
		System.out.println(opt);
		
		opt.setVendor("OpenJDK");
		opt.setCollector("G1");
		System.out.println(opt);
		
		opt.setVendor("IBM");
		opt.setCollector("CMS");
		System.out.println(opt);
		
		opt.setVendor("IBM");
		opt.setCollector("Parallel");
		System.out.println(opt);
		
		opt.setVendor("IBM");
		opt.setCollector("G1");
		System.out.println(opt);
		
		
	}

}
