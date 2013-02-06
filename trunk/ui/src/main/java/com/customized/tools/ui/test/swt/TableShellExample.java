package com.customized.tools.ui.test.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TableShellExample {
	Display d;

	Shell s;

	TableShellExample() {
		d = new Display();
		s = new Shell(d);

		s.setSize(250, 200);

		s.setText("A Table Shell Example");
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		s.setLayout(gl);

		final Table t = new Table(s, SWT.BORDER | SWT.CHECK | SWT.MULTI | SWT.FULL_SELECTION);
		final GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		t.setLayoutData(gd);
		t.setHeaderVisible(true);
		final TableColumn tc1 = new TableColumn(t, SWT.LEFT);
		final TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		final TableColumn tc3 = new TableColumn(t, SWT.CENTER);
		tc1.setText("First Name");
		tc2.setText("Last Name");
		tc3.setText("Address");
		tc1.setWidth(70);
		tc2.setWidth(70);
		tc3.setWidth(80);
//		final TableItem item1 = new TableItem(t, SWT.NONE);
//		item1.setText(new String[] { "Tim", "Hatton", "Kentucky" });
//		final TableItem item2 = new TableItem(t, SWT.NONE);
//		item2.setText(new String[] { "Caitlyn", "Warner", "Ohio" });
//		final TableItem item3 = new TableItem(t, SWT.NONE);
//		item3.setText(new String[] { "Reese", "Miller", "Ohio" });

//		final Text input = new Text(s, SWT.SINGLE | SWT.BORDER);
//		final Button searchBtn = new Button(s, SWT.BORDER | SWT.PUSH);
//		searchBtn.setText("Search");
//		searchBtn.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				TableItem[] tia = t.getItems();
//
//				for (int i = 0; i < tia.length; i++) {
//					if (tia[i].getText(2).equals(input.getText())) {
//						tia[i].setBackground(new Color(d, 127, 178, 127));
//					}
//
//				}
//			}
//		});

		s.open();
		while (!s.isDisposed()) {
			if (!d.readAndDispatch())
				d.sleep();
		}
		d.dispose();
	}

	public static void main(String[] argv) {
		new TableShellExample();
	}
}
