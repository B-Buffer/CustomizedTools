package com.customized.tools.dbtester.metdata;

import java.util.ArrayList;
import java.util.List;

import com.customized.tools.dbtester.metdata.AbstractMetadataRecord.Modifiable;

public class Procedure extends AbstractMetadataRecord implements Modifiable {

	private static final long serialVersionUID = -1632308845162222651L;

	public enum Type {
		Function,
		UDF,
		StoredProc,
		StoredQuery
	}
	
    private boolean isFunction;
    private boolean isVirtual;
    private int updateCount = 1;
    private List<ProcedureParameter> parameters = new ArrayList<ProcedureParameter>(2);
    private ColumnSet<Procedure> resultSet;
    private volatile String queryPlan;
    
    private Schema parent;
    private volatile transient long lastModified;
    
    public void setParent(Schema parent) {
		this.parent = parent;
	}
    
    public boolean isFunction() {
        return isFunction;
    }

    public boolean isVirtual() {
        return this.isVirtual;
    }

    public Type getType() {
    	if (isFunction()) {
        	if (isVirtual()) {
        		return Type.UDF;
        	}
        	return Type.Function;
        }
        if (isVirtual()) {
            return Type.StoredQuery;
        }
        return Type.StoredProc;
    }
    
    public int getUpdateCount() {
        return this.updateCount;
    }
    
	public List<ProcedureParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ProcedureParameter> parameters) {
		this.parameters = parameters;
	}

	public String getQueryPlan() {
		return queryPlan;
	}

	public void setQueryPlan(String queryPlan) {
		this.queryPlan = queryPlan;
	}

    /**
     * @param b
     */
    public void setFunction(boolean b) {
        isFunction = b;
    }

    /**
     * @param b
     */
    public void setVirtual(boolean b) {
        isVirtual = b;
    }
    
    public void setUpdateCount(int count) {
    	this.updateCount = count;
    }

	public void setResultSet(ColumnSet<Procedure> resultSet) {
		this.resultSet = resultSet;
		if (resultSet != null) {
			resultSet.setParent(this);
		}
	}

	public ColumnSet<Procedure> getResultSet() {
		return resultSet;
	}
	
	@Override
	public Schema getParent() {
		return parent;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

}
