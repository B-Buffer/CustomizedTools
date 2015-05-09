package com.customized.tools.renderer;

import java.io.PrintStream;

public class TerminalOutputDevice extends PrintStreamOutputDevice {
	
	private static final String BOLD   = "\033[1m";
    private static final String NORMAL = "\033[m";
    private static final String GREY   = "\033[1;30m";

	public TerminalOutputDevice(PrintStream out) {
		super(out);
	}
	
	public void attributeBold()  { 
        print( BOLD );
    }
    public void attributeGrey()  { 
        print( GREY );
    }

    public void attributeReset() { 
        print( NORMAL );
    }

    public boolean isTerminal() {
        return true;
    }

}
