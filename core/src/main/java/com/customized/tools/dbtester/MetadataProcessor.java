package com.customized.tools.dbtester;

import com.customized.tools.dbtester.metdata.Metadata;


public interface MetadataProcessor<C> {
	
	public void process(Metadata metadata, C connection) throws Exception;

}
