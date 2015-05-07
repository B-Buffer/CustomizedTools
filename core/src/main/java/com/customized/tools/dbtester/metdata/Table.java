package com.customized.tools.dbtester.metdata;

import java.util.ArrayList;
import java.util.List;

public class Table extends ColumnSet{
	
	public static final long UNKNOWN_CARDINALITY = -1;
	
	private Type tableType;
	
	private KeyRecord primaryKey;
	
	private boolean supportsUpdate;
	
	private String annotation;
	
	private String simpleName;
	
	private volatile long cardinality = UNKNOWN_CARDINALITY;
	
	private List<ForeignKey> foriegnKeys = new ArrayList<ForeignKey>(2);
	private List<KeyRecord> indexes = new ArrayList<KeyRecord>(2);
    private List<KeyRecord> functionBasedIndexes = new ArrayList<KeyRecord>(2);
    private List<KeyRecord> uniqueKeys = new ArrayList<KeyRecord>(2);
    private List<KeyRecord> accessPatterns = new ArrayList<KeyRecord>(2);
	
	public Type getTableType() {
		
		if (tableType == null) {
    		return Type.Table;
    	}
		
		return tableType;
	}

	public void setTableType(Type tableType) {
		this.tableType = tableType;
	}

	

	public boolean isSupportsUpdate() {
		return supportsUpdate;
	}

	public void setSupportsUpdate(boolean supportsUpdate) {
		this.supportsUpdate = supportsUpdate;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public KeyRecord getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(KeyRecord primaryKey) {
		this.primaryKey = primaryKey;
	}

	public long getCardinality() {
		return cardinality;
	}

	public void setCardinality(long cardinality) {
		this.cardinality = cardinality;
	}
	
	public float getCardinalityAsFloat() {
    	return asFloat((int)cardinality);
    }
	
	static final float asFloat(int i) {
		if (i >= UNKNOWN_CARDINALITY) {
    		return i;
    	}
    	return Float.intBitsToFloat(i & 0x7fffffff);
	}

	public List<KeyRecord> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<KeyRecord> indexes) {
		this.indexes = indexes;
	}

	public List<KeyRecord> getFunctionBasedIndexes() {
		return functionBasedIndexes;
	}

	public void setFunctionBasedIndexes(List<KeyRecord> functionBasedIndexes) {
		this.functionBasedIndexes = functionBasedIndexes;
	}

	public List<KeyRecord> getUniqueKeys() {
		return uniqueKeys;
	}

	public void setUniqueKeys(List<KeyRecord> uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}

	public List<KeyRecord> getAccessPatterns() {
		return accessPatterns;
	}

	public void setAccessPatterns(List<KeyRecord> accessPatterns) {
		this.accessPatterns = accessPatterns;
	}

	public List<ForeignKey> getForiegnKeys() {
		return foriegnKeys;
	}

	public void setForiegnKeys(List<ForeignKey> foriegnKeys) {
		this.foriegnKeys = foriegnKeys;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public enum Type {
		Table,
		View,
		Document,
		XmlMappingClass,
		XmlStagingTable,
		MaterializedTable,
		TemporaryTable
	}

}
