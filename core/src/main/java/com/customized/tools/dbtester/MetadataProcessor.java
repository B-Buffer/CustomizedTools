package com.customized.tools.dbtester;

import com.customized.tools.dbtester.metdata.Metdata;


public interface MetadataProcessor<C> {
	
	public void process(Metdata metadata, C connection) throws Exception;

}
