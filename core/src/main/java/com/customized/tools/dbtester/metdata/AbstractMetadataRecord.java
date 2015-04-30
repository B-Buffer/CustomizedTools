package com.customized.tools.dbtester.metdata;

import java.util.concurrent.atomic.AtomicLong;

public class AbstractMetadataRecord {
	
	private static AtomicLong UUID_SEQUENCE = new AtomicLong();
	
	private String name; 
	
	private String uuid;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		if (uuid == null) {
			uuid = String.valueOf(UUID_SEQUENCE.getAndIncrement());
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
