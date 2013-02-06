package com.customized.tools.ui.swt;

public class Column {

	private String text = "null";
	
	private int width = 50 ;

	public Column() {
		super();
	}

	public Column(String text) {
		super();
		this.text = text;
	}

	public Column(String text, int width) {
		this.text = text;
		this.width = width;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
