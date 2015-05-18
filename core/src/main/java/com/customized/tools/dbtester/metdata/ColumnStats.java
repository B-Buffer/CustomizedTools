package com.customized.tools.dbtester.metdata;

import java.io.Serializable;

public class ColumnStats implements Serializable {

	private static final long serialVersionUID = 9112912572482103294L;
	
	private Number distinctValues;
    private Number nullValues;
    private String minimumValue;
    private String maximumValue;
	
	public String getMinimumValue() {
		return minimumValue;
	}
	
	public void setMinimumValue(String min) {
		this.minimumValue = min;
	}
	
	public String getMaximumValue() {
		return maximumValue;
	}
	
	public void setMaximumValue(String max) {
		this.maximumValue = max;
	}

	public Number getDistinctValues() {
		return distinctValues;
	}

	public void setDistinctValues(Number numDistinctValues) {
		this.distinctValues = numDistinctValues;
	}

	public Number getNullValues() {
		return nullValues;
	}

	public void setNullValues(Number numNullValues) {
		this.nullValues = numNullValues;
	}

}
