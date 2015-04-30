package com.customized.tools.dbtester.metdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ColumnSet extends AbstractMetadataRecord {
	
	private List<Column> columns;
	private transient Map<String, Column> columnMap;
	
	
	public List<Column> getColumns() {
    	return columns;
    }
	
	public Column getColumnByName(String name) {
    	if (columns == null || name == null) {
    		return null;
    	}
    	Map<String, Column> map = columnMap;
    	if (map == null) {
    		map = new TreeMap<String, Column>(String.CASE_INSENSITIVE_ORDER);
    		for (Column c : columns) {
				map.put(c.getName(), c);
			}
    		columnMap = map;
    	}
    	return map.get(name);
    }
	
	public void addColumn(Column column) {
    	if (columns == null) {
    		columns = new ArrayList<Column>();
    	}
    	columns.add(column);
    	Map<String, Column> map = columnMap;
    	if (map != null) {
    		map.put(column.getName(), column);
    	}
    }
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
		columnMap = null;
	}

}
