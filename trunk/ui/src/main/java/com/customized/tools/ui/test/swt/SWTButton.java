package com.customized.tools.ui.test.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SWTButton {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		
		Button button =  new Button(shell, SWT.PUSH);
		button.setText("  Push  ");
		button.addSelectionListener(new SelectionAdapter() {
		  @Override
		  public void widgetSelected(SelectionEvent e) {
		    // Handle the selection event
		    System.out.println("Called!");
		  }
		});
		
		button.pack();
		shell.open();
		// Create and check the event loop
		while (!shell.isDisposed()) {
		  if (!display.readAndDispatch())
		    display.sleep();
		}
		display.dispose();
	}

}
