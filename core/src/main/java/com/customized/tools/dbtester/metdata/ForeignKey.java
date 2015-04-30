package com.customized.tools.dbtester.metdata;

import java.util.ArrayList;
import java.util.List;

public class ForeignKey extends KeyRecord{
	
	private String uniqueKeyID;
	
	private KeyRecord primaryKey;
	
	private List<String> referenceColumns;
	
	private String referenceTableName;

	public ForeignKey() {
		super(Type.Foreign);
	}
	
	public KeyRecord getReferenceKey() {
    	return this.primaryKey;
    }
	
	public void setReferenceKey(KeyRecord primaryKey) {
    	this.primaryKey = primaryKey;
		if (this.primaryKey != null) {
			this.referenceColumns = new ArrayList<String>();
			for (Column c : primaryKey.getColumns()) {
				this.referenceColumns.add(c.getName());
			}
			if (primaryKey.getParent() != null) {
				this.referenceTableName = primaryKey.getParent().getName();
			}
			this.uniqueKeyID = primaryKey.getUuid();
		} else {
			this.referenceColumns = null;
			this.referenceTableName = null;
			this.uniqueKeyID = null;
		}
    }

	public String getUniqueKeyID() {
		return uniqueKeyID;
	}

	public void setUniqueKeyID(String uniqueKeyID) {
		this.uniqueKeyID = uniqueKeyID;
	}
	
	public String getReferenceTableName() {
		return referenceTableName;
	}

	public void setReferenceTableName(String tableName) {
		this.referenceTableName = tableName;
	}
	
	public List<String> getReferenceColumns() {
		return referenceColumns;
	}

	public void setReferenceColumns(List<String> referenceColumns) {
		this.referenceColumns = referenceColumns;
	}
}
