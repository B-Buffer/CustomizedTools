package com.customized.tools.dbtester.metdata;

import java.util.ArrayList;
import java.util.List;

public class Procedure extends AbstractMetadataRecord {
	
	private List<ProcedureParameter> parameters = new ArrayList<ProcedureParameter>(2);
	
	public List<ProcedureParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ProcedureParameter> parameters) {
		this.parameters = parameters;
	}
	
	public enum Type {
		Function,
		UDF,
		StoredProc,
		StoredQuery
	}

}
