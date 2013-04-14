package com.customized.tools.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * 
 * Default inplementation of SWT Table
 * 
 * @author kylin
 *
 */
public class DefaultTableImpl extends AbstractTable {
	
	public DefaultTableImpl(String title, String image){
		super(title, image);
	}
	
	public DefaultTableImpl(int width, int height, String title, String image){
		super(width, height, title, image);
	}

	protected void fillTableContent(Table table) {
	    
	    List<Column> columns = new ArrayList<Column>();
	    columns.add(new Column("ID", 150));
	    columns.add(new Column("Name", 150));
	    columns.add(new Column("Alias", 150));
	    
	    fillTableColumns(columns, table);
	    
	    List<String[]> items = new ArrayList<String[]>();
	    items.add(new String[]{"001", "Kylin Soong", "Red Hat Inc"});
	    items.add(new String[]{"002", "Kobe Bryant", "LA Lakers"});
	    items.add(new String[]{"003", "Coco Lee", "Female Singer"});
	    
	    fillTableItems(items, table);
	}
	
	protected void fillTableItems(List<String[]> items, Table table) {
		for(String[] strs : items) {
			final TableItem item = new TableItem(table, SWT.NONE);
		    item.setText(strs);
		}
	} 

	protected void fillTableColumns(List<Column> columns, Table table) {
				
		for(Column c : columns) {
			final TableColumn tc = new TableColumn(table, SWT.CENTER);
			tc.setText(c.getText());
			tc.setWidth(c.getWidth());
		}
	}

	protected void fillToolBarItem(ToolBar toolBar) {
		ToolItem add = new ToolItem(toolBar, SWT.PUSH);
		add.setText(" Add ");
		
		ToolItem delete = new ToolItem(toolBar, SWT.PUSH);
		delete.setText(" Delete ");
		
		ToolItem modify = new ToolItem(toolBar, SWT.PUSH);
		modify.setText("Modify");
		
		ToolItem search = new ToolItem(toolBar, SWT.PUSH);
		search.setText("Search");
		
		ToolItem refresh = new ToolItem(toolBar, SWT.PUSH);
		refresh.setText("Refresh");
		
		ToolItem help = new ToolItem(toolBar, SWT.PUSH);
		help.setText(" Help ");
	}
	
}