package com.customized.tools.dbtester.metdata;

public class KeyRecord extends ColumnSet {
	
	public enum Type {
		Primary,
		Foreign,
		Unique, //constraint
		AccessPattern,
		Index,
	}
	
	private Type type;
	
	private Table parent;

	public KeyRecord(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}

	public Table getParent() {
		return parent;
	}

	public void setParent(Table parent) {
		this.parent = parent;
	}

}
