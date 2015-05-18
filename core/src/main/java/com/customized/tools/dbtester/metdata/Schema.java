package com.customized.tools.dbtester.metdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Schema extends AbstractMetadataRecord {

	private static final long serialVersionUID = -7903904290163700647L;
	
	private boolean physical = true;
    private String primaryMetamodelUri = "http://www.metamatrix.com/metamodels/Relational"; //$NON-NLS-1$
    
    private NavigableMap<String, Table> tables = new TreeMap<String, Table>(String.CASE_INSENSITIVE_ORDER);
	private NavigableMap<String, Procedure> procedures = new TreeMap<String, Procedure>(String.CASE_INSENSITIVE_ORDER);
	private NavigableMap<String, FunctionMethod> functions = new TreeMap<String, FunctionMethod>(String.CASE_INSENSITIVE_ORDER);
	
	private List<AbstractMetadataRecord> resolvingOrder = new ArrayList<AbstractMetadataRecord>();
	
	public void addTable(Table table) {
		table.setParent(this);
		if (this.tables.put(table.getName(), table) != null) {
			throw new DuplicateRecordException(); 
		}
		resolvingOrder.add(table);
	}
	
	public void addProcedure(Procedure procedure) {
		procedure.setParent(this);
		if (this.procedures.put(procedure.getName(), procedure) != null) {
			throw new DuplicateRecordException(); 
		}
		resolvingOrder.add(procedure);
	}
	
	public void addFunction(FunctionMethod function) {
		function.setParent(this);
		//TODO: ensure that all uuids are unique
		if (this.functions.put(function.getUUID(), function) != null) {
			throw new DuplicateRecordException();
		}
		resolvingOrder.add(function);
	}	

	/**
	 * Get the tables defined in this schema
	 * @return
	 */
	public NavigableMap<String, Table> getTables() {
		return tables;
	}
	
	public Table getTable(String tableName) {
		return tables.get(tableName);
	}
	
	/**
	 * Get the procedures defined in this schema
	 * @return
	 */
	public NavigableMap<String, Procedure> getProcedures() {
		return procedures;
	}
	
	public Procedure getProcedure(String procName) {
		return procedures.get(procName);
	}
	
	/**
	 * Get the functions defined in this schema in a map of uuid to {@link FunctionMethod}
	 * @return
	 */
	public NavigableMap<String, FunctionMethod> getFunctions() {
		return functions;
	}
	
	/**
	 * Get a function by uid
	 * @param funcName
	 * @return
	 */
	public FunctionMethod getFunction(String uid) {
		return functions.get(uid);
	}	
	
    public String getPrimaryMetamodelUri() {
        return primaryMetamodelUri;
    }

    public boolean isPhysical() {
        return physical;
    }

    /**
     * @param string
     */
    public void setPrimaryMetamodelUri(String string) {
        primaryMetamodelUri = string;
    }

    public void setPhysical(boolean physical) {
		this.physical = physical;
	}
    
    /**
     * 7.1 schemas did not have functions
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
    	in.defaultReadObject();
    	if (this.functions == null) {
    		this.functions = new TreeMap<String, FunctionMethod>(String.CASE_INSENSITIVE_ORDER);
    	}
    	if (this.resolvingOrder == null) {
    		this.resolvingOrder = new ArrayList<AbstractMetadataRecord>();
    		this.resolvingOrder.addAll(this.tables.values());
    		this.resolvingOrder.addAll(this.procedures.values());
    		this.resolvingOrder.addAll(this.functions.values());
    	}
    }
    
    public List<AbstractMetadataRecord> getResolvingOrder() {
		return resolvingOrder;
	}

}
