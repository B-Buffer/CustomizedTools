package com.customized.tools.dbtester.view;

public interface OutputDevice {
	
	void flush();
    void write(byte[] buffer, int off, int len);
    void print(String s);
    void println(String s);
    void println();

    void attributeBold();
    void attributeReset();
    void attributeGrey();
    
    void close();

    boolean isTerminal();

}
