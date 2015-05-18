package com.customized.tools.dbtester.metdata;

import java.io.Serializable;

public class AggregateAttributes implements Serializable {

	private static final long serialVersionUID = 6693275134976882775L;
	
	private boolean analytic;
	private boolean usesDistinctRows;
	private boolean allowsOrderBy;
	private boolean allowsDistinct;
	private boolean decomposable;
	/**
	 * @return true if the aggregate allows an order by clause
	 */
	public boolean allowsOrderBy() {
		return allowsOrderBy;
	}
	
	public void setAllowsOrderBy(boolean allowsOrderBy) {
		this.allowsOrderBy = allowsOrderBy;
	}
	
	/**
	 * @return true if the aggregate can only be used as a windowed function
	 */
	public boolean isAnalytic() {
		return analytic;
	}
	
	public void setAnalytic(boolean analytic) {
		this.analytic = analytic;
	}
	
	/**
	 * 
	 * @return True if the aggregate function specified without the
	 * distinct keyword effectively uses only distinct rows.  
	 * For example min/max would return true
	 * and avg would return false.
	 */
	public boolean usesDistinctRows() {
		return usesDistinctRows;
	}
	
	public void setUsesDistinctRows(boolean usesDistinctRows) {
		this.usesDistinctRows = usesDistinctRows;
	}
	
	/**
	 * @return true if the aggregate function may be decomposed as
	 * agg(agg(x)) for non-partitioned aggregate pushdown.
	 * This is only meaningful for single argument aggregate
	 * functions. 
	 */
	public boolean isDecomposable() {
		return decomposable;
	}
	
	public void setDecomposable(boolean decomposable) {
		this.decomposable = decomposable;
	}
	
	/**
	 * @return true if the aggregate function can use the DISTINCT keyword
	 */
	public boolean allowsDistinct() {
		return allowsDistinct;
	}
	
	public void setAllowsDistinct(boolean allowsDistinct) {
		this.allowsDistinct = allowsDistinct;
	}

}
