package com.customized.tools.dbtester.renderer;

public interface Renderer {
	
	void addRow(Column[] row);
	
	void renderer();
	
	void flush();
	
	ColumnMetaData[] getMetaData();

}
