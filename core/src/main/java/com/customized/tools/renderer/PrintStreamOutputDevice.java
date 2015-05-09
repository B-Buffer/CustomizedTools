package com.customized.tools.renderer;

import java.io.PrintStream;

public class PrintStreamOutputDevice implements OutputDevice {
	
	private final PrintStream out;

	public PrintStreamOutputDevice(PrintStream out) {
		super();
		this.out = out;
	}

	@Override
	public void flush() {
		out.flush();
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		out.write(buf, off, len);
	}

	@Override
	public void print(String s) {
		out.print(s);
	}

	@Override
	public void println(String s) {
		out.println(s);
	}

	@Override
	public void println() {
		out.println();
	}

	@Override
	public void attributeBold() {
		
	}

	@Override
	public void attributeReset() {
		
	}

	@Override
	public void attributeGrey() {
		
	}

	@Override
	public void close() {
		out.close();
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

}
