package com.customized.tools.ui.test.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The Display and Shell classes are key components of SWT applications.
 *   A org.eclipse.swt.widgets.Shell class represents a window.
 *   The org.eclipse.swt.widgets.Display class is responsible for managing event loops, for controlling the communication between 
 * the UI thread and other threads and for managing fonts and colors. Display is the basis for all SWT capabilities. 
 * 
 * An event loop is needed to communicate user input events from the underlying native operating system widgets to the SWT event system. 
 * SWT does not provide its own event loop. This means that the programmer has to explicitly start and check the event loop to update the 
 * user interface. The loop will execute the readAndDispatch() method which reads events from the native widgets and dispatches them to the 
 * SWT event system. The loop is executed until the main shell is closed. If this loop was left out, the application would terminate immediately. 
 * 
 * @author kylin
 *
 */
public class SWTEventLoop {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();
		// Create and check the event loop
		while (!shell.isDisposed()) {
		  if (!display.readAndDispatch())
		    display.sleep();
		}
		display.dispose(); 
	}

}
