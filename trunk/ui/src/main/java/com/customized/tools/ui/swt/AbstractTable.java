package com.customized.tools.ui.swt;

import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.customized.tools.common.ResourceLoader;

public abstract class AbstractTable {
	
	private static final int WIDTH = 800 ;
	private static final int HEIGHT = 600 ;
	
	Display display = new Display();
	Shell shell = new Shell(display);
	Table table = new Table(shell, SWT.BORDER | SWT.CHECK | SWT.MULTI | SWT.FULL_SELECTION);
	ToolBar toolBar = new ToolBar(shell, SWT.BORDER | SWT.HORIZONTAL);
	
	public AbstractTable() {
		this("", null);
	}
	
	public AbstractTable(String title, String image) {
		this(WIDTH, HEIGHT, title, image) ;
	}
	
	public AbstractTable(int width, int height, String title, String image) {
		
		if (width == 0)
			width = WIDTH;

		if (height == 0)
			height = HEIGHT;
		
		shell.setSize(width, height);
		shell.setText(title);
		shell.setImage(new Image(display, getResourceAsStream(image)));
	
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2 ;
		shell.setLayout(gridLayout);
		
	}
	
	private InputStream getResourceAsStream(String name) {
		return ResourceLoader.getInstance().getResourceAsStream(name);
	}

	protected abstract void customizeTable(Table table );
	
	public void start() {
		
		customizeTable(table);
		
		fillToolBarItem(toolBar);
		
		fillButton(shell);
				
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	protected void fillToolBarItem(ToolBar toolBar) {
		
		ToolItem delete = new ToolItem(toolBar, SWT.PUSH);
		delete.setText("Delete");
		
		ToolItem clear = new ToolItem(toolBar, SWT.PUSH);
		clear.setText(" Clear ");
		
		ToolItem refresh = new ToolItem(toolBar, SWT.PUSH);
		refresh.setText("Refresh");
		
		ToolItem help = new ToolItem(toolBar, SWT.PUSH);
		help.setText(" Help ");
	}

	protected void fillButton(Shell shell) {
		
//		final Button deleteBtn = new Button(shell, SWT.BORDER | SWT.PUSH);
//		deleteBtn.setText(" Delete ");
		
//		final Button clearBtn = new Button(shell, SWT.BORDER | SWT.PUSH);
//		clearBtn.setText(" Clear ");
//
//		final Button refreshBtn = new Button(shell, SWT.BORDER | SWT.PUSH);
//		refreshBtn.setText("Refresh");
		
	
		
	}

	public void stop() {
		display.dispose();
	}

}
