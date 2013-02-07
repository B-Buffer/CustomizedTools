package com.customized.tools.ui.swt.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.customized.tools.ui.swt.AbstractTable;

public class MyTable extends AbstractTable{
	
	public MyTable(String title, String image) {
		super(title, image);
	}

	public static void main(String[] args) {
				
		new MyTable("Infinispan Demo", "infinispan_icon_32px.gif").start();
	}

	protected void fillTableContent(Table table ) {
		
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
	    final TableItem item1 = new TableItem(table, SWT.NONE);
	    item1.setText(new String[]{"key", "value", "-1", "-1", "localhost-54200"});
	    
	    final TableItem item2 = new TableItem(table, SWT.NONE);
	    item2.setText(new String[]{"key", "value", "-1", "-1", "localhost-54200"});
	    
	    final TableItem item3 = new TableItem(table, SWT.NONE);
	    item3.setText(new String[]{"key", "value", "-1", "-1", "localhost-54200"});
	    
	}

	protected void fillToolBarItem(ToolBar toolBar) {
		ToolItem delete = new ToolItem(toolBar, SWT.PUSH);
		delete.setText("Delete");
		
		ToolItem clear = new ToolItem(toolBar, SWT.PUSH);
		clear.setText(" Clear ");
		
		ToolItem refresh = new ToolItem(toolBar, SWT.PUSH);
		refresh.setText("Refresh");
	}

}
