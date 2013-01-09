package com.customized.tools.gcviewer.test;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.gcviewer.GCViewerWrapper;

public class GCViewerTester {

	public static void main(String[] args) {

		new GCViewerWrapper(new InputConsole()).execute();
	}

}
