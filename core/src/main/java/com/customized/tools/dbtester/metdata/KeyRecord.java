package com.customized.tools.dbtester.metdata;

public class KeyRecord extends ColumnSet<Table> {
	
	public enum Type {
		Primary,
		Foreign,
		Unique, //constraint
		@Deprecated
		NonUnique,
		AccessPattern,
		Index,
	}
	
	private Type type;

	public KeyRecord(Type type) {
		if (type == Type.NonUnique) {
			type = Type.Index;
		}
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}

}
