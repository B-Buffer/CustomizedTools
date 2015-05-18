package com.customized.tools.dbtester.metdata;

import java.io.Serializable;

public class TableStats implements Serializable {

	private static final long serialVersionUID = -6280511348641398827L;
	
	private Number cardinality;
	
	public Number getCardinality() {
		return cardinality;
	}
	
	public void setCardinality(Number cardinality) {
		this.cardinality = cardinality;
	}


}
