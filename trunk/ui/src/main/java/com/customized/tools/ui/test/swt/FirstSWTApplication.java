package com.customized.tools.ui.test.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author kylin
 * 
 */
public class FirstSWTApplication {
	
	Display display;
	Shell shell;
	
	public FirstSWTApplication() {
		display = new Display();
		shell = new Shell(display);
	}
	
	void testMessageBox() {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
		dialog.setText("My info");
		dialog.setMessage("Do you really want to do this?");
		int code = dialog.open();
		System.out.println(code);
	}
	
	public void start() {
		
		testMessageBox();
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void main(String[] args) {
			
		new FirstSWTApplication().start();
	}

}
