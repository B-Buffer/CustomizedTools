package com.customized.tools.dbtester.metdata;

public class Column extends AbstractMetadataRecord{
	
	public enum NullType {
		No_Nulls {
			@Override
			public String toString() {
				return "No Nulls"; 
			}
		},
		Nullable,
		Unknown		
	}
	
	private Table parent;
	
	private int position;
	
	private int precision;
	
	private int length;
	
    private int radix;
    
    private int charOctetLength;
	
	private String type;
	
	private String nativeType;
	
	private String annotation;
	
	 private String defaultValue;
	
	private NullType nullType;
	
	private boolean updatable;
	
	private boolean autoIncremented;

	public Table getParent() {
		return parent;
	}

	public void setParent(Table parent) {
		this.parent = parent;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getLength() {
		return length;
	}

	public String getNativeType() {
		return nativeType;
	}

	public void setNativeType(String nativeType) {
		this.nativeType = nativeType;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getRadix() {
		return radix;
	}

	public void setRadix(int radix) {
		this.radix = radix;
	}

	public NullType getNullType() {
		return nullType;
	}

	public void setNullType(NullType nullType) {
		this.nullType = nullType;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public boolean isAutoIncremented() {
		return autoIncremented;
	}

	public void setAutoIncremented(boolean autoIncremented) {
		this.autoIncremented = autoIncremented;
	}

}
