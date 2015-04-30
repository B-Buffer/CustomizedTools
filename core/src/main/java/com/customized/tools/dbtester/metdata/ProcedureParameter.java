package com.customized.tools.dbtester.metdata;

public class ProcedureParameter extends AbstractMetadataRecord {
	
	public enum Type {
		In,
		InOut,
		Out,
		ReturnValue
	}

}
