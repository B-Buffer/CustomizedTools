package com.customized.tools.ui.swt.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.customized.tools.ui.swt.AbstractTable;

public class MyTable extends AbstractTable{
	
	public MyTable(String title, String image) {
		super(title, image);
	}

	public static void main(String[] args) {
				
		new MyTable("Infinispan Demo", "infinispan_icon_32px.gif").start();
	}

	protected void customizeTable(Table table ) {
		
		final GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.horizontalSpan = 2;
	    table.setLayoutData(gd);
	    table.setHeaderVisible(true);
	    final TableColumn tc1 = new TableColumn(table, SWT.CENTER);
	    final TableColumn tc2 = new TableColumn(table, SWT.CENTER);
	    final TableColumn tc3 = new TableColumn(table, SWT.CENTER);
	    final TableColumn tc4 = new TableColumn(table, SWT.CENTER);
	    final TableColumn tc5 = new TableColumn(table, SWT.CENTER);
	    tc1.setText("Key");
	    tc2.setText("Value");
	    tc3.setText("Lifespan");
	    tc4.setText("MaxIdle");
	    tc5.setText("Alias");
	    tc1.setWidth(100);
	    tc2.setWidth(200);
	    tc3.setWidth(100);
	    tc4.setWidth(100);
	    tc5.setWidth(100);
	    final TableItem item = new TableItem(table, SWT.NONE);
	    item.setText(new String[]{"key", "value", "-1", "-1", "localhost-54200"});
	    
	}

}
